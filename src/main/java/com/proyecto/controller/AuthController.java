package com.proyecto.controller;

import com.proyecto.domain.User;
import com.proyecto.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Vista del formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register"; // ← templates/auth/register.html
    }

    // Procesamiento del formulario de registro
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // puedes cambiar a ADMIN si quieres
        userRepository.save(user);
        return "redirect:/login?registered";
    }

    // Vista del login
    @GetMapping("/login")
    public String login() {
        return "auth/login"; // ← templates/auth/login.html
    }
}
