package com.proyecto.repository;

import com.proyecto.domain.Cabana;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CabanaRepository extends JpaRepository<Cabana, Integer> {

    public void deleteById(Integer id);
    
}
