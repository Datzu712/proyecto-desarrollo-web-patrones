package com.dream.restapi.repository;

import com.dream.restapi.model.CabinImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinImageRepository extends JpaRepository<CabinImage, Integer> {
    List<CabinImage> findByCabinId(Integer cabinId);
}