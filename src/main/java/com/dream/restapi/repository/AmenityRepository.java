package com.dream.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dream.restapi.model.Amenity;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {}
