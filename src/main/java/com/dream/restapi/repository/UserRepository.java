package com.dream.restapi.repository;

import com.dream.restapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
