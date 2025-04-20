package com.proyecto.repository;

import com.proyecto.domain.Booking;
import com.proyecto.domain.Cabana;
import com.proyecto.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCabanaId(Integer cabanaId);
    List<Booking> findByCabanaAndStatusNot(Cabana cabana, String status);
    List<Booking> findByCustomer(User customer);
    List<Booking> findByCabanaIdAndStatus(Integer cabanaId, String status);
}
