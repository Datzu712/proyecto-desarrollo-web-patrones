package com.proyecto.service;

import com.proyecto.domain.Booking;
import com.proyecto.domain.Cabana;
import com.proyecto.domain.User;
import com.proyecto.repository.BookingRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> obtenerReservasPorCabana(Integer cabanaId) {
        return bookingRepository.findByCabanaId(cabanaId);
    }

    public List<Booking> obtenerReservasPorUsuario(User user) {
        List<Booking> reservas = bookingRepository.findByCustomer(user);

        return reservas.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Booking obtenerReservasPorId(Integer id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }


    public Booking guardarBooking(Booking booking) {
        if (booking.getCabana() == null) {
            throw new IllegalArgumentException("La reserva debe tener una cabaña asociada");
        }
        return bookingRepository.save(booking);
    }

}
