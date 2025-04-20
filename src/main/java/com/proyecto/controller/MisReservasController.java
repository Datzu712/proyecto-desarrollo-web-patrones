package com.proyecto.controller;

import com.proyecto.domain.Booking;
import com.proyecto.domain.User;
import com.proyecto.repository.UserRepository;
import com.proyecto.service.BookingService;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller

public class MisReservasController {

    private final UserRepository userRepository;
    private final BookingService bookingService;

    public MisReservasController(UserRepository userRepository, BookingService bookingService) {
        this.userRepository = userRepository;
        this.bookingService = bookingService;
    }

    @GetMapping("/misReservas")
    public String verMisReservas(Authentication authentication, Model model) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            List<Booking> reservas = bookingService.obtenerReservasPorUsuario(user);

            if (reservas == null) {
                reservas = Collections.emptyList();
            }

            model.addAttribute("reservas", reservas);

            return "reservasUsuario/listado";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar reservas: " + e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarReserva(
            @PathVariable Integer id,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Booking reserva = bookingService.obtenerReservasPorId(id);
            
            if (!reserva.getCustomer().getId().equals(user.getId())) {
                throw new RuntimeException("No tienes permiso para cancelar esta reserva");
            }

            reserva.setStatus("CANCELADA");
            bookingService.guardarBooking(reserva);

            redirectAttributes.addFlashAttribute("success", "Reserva cancelada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cancelar reserva: " + e.getMessage());
        }

        return "redirect:/misReservas";
    }
}
