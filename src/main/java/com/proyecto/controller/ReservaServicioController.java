package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/servicios/reserva")
public class ReservaServicioController {
    @GetMapping("/listado")
    public String mostrarListado(){
        return "servicios/reserva/listado";
    }
    
@PostMapping("/guardar")
public String guardarReserva(RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("exito", true);
    return "redirect:/servicios/reserva/listado";
}
    
}
