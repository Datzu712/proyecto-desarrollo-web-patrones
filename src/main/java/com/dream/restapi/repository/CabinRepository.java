package com.dream.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dream.restapi.model.Cabin;

@Repository
public interface CabinRepository extends JpaRepository<Cabin, Integer>  {}
