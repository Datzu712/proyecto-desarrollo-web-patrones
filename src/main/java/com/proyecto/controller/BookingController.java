package com.proyecto.controller;

import com.proyecto.domain.Booking;
import com.proyecto.domain.Cabana;
import com.proyecto.domain.User;
import com.proyecto.service.BookingService;
import com.proyecto.service.CabanaService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.proyecto.repository.UserRepository;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final CabanaService cabanaService;
    private final UserRepository userRepository;

    public BookingController(BookingService bookingService, CabanaService cabanaService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.cabanaService = cabanaService;
        this.userRepository = userRepository;
    }


    @GetMapping("/reserva")
    public String showBookingForm(@RequestParam Integer cabanaId, Model model) {
        Cabana cabana = cabanaService.obtenerCabanaPorId(cabanaId);
        model.addAttribute("cabana", cabana);
        model.addAttribute("booking", new Booking()); 
        return "bookings/reserva";
    }


    @PostMapping("/reserva")
    public String submitBooking(
            @ModelAttribute("booking") Booking booking,
            @RequestParam("cabanaId") Integer cabanaId,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        try {
            
            if (booking.getStartDate() == null || booking.getEndDate() == null) {
                redirectAttributes.addFlashAttribute("error", "Las fechas son obligatorias");
                return "redirect:/bookings/reserva?cabanaId=" + booking.getCabana().getId();
            }

            
            Cabana cabana = cabanaService.obtenerCabanaPorId(cabanaId);
            if (cabana == null) {
                redirectAttributes.addFlashAttribute("error", "Cabaña no encontrada");
                return "redirect:/cabanas";
            }
          
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            long nights = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
            BigDecimal totalAmount = cabana.getPrice().multiply(BigDecimal.valueOf(nights));

           
            booking.setNights((int)nights);
            booking.setCabana(cabana);
            booking.setCustomer(user);
            booking.setTotalAmount(totalAmount);
            booking.setStatus("CONFIRMADA");
            Booking savedBooking = bookingService.guardarBooking(booking);

            
            redirectAttributes.addFlashAttribute("booking", savedBooking);
            redirectAttributes.addFlashAttribute("success", "Reserva creada exitosamente");

            return "redirect:/bookings/reserva?cabanaId=" + savedBooking.getCabana().getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear reserva: " + e.getMessage());
            return "redirect:/bookings/reserva?cabanaId=" + booking.getCabana().getId();
        }
    }
}
