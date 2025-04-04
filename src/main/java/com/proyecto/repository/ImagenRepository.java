package com.proyecto.repository;

import com.proyecto.domain.Imagen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findByCabanaId(Integer cabanaId);
}
