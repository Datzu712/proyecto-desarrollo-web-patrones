package com.dream.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dream.restapi.dto.AuthRegisterBody;
import com.dream.restapi.dto.AuthLoginReqBody;
import com.dream.restapi.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthLoginReqBody authReqBody, HttpServletResponse res) {
        return authService.login(authReqBody.getEmail(), authReqBody.getPassword(), res);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse res) {
        return authService.logout(res);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRegisterBody user) {
        return authService.register(user);
    }
}
