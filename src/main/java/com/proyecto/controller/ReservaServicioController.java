package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/servicios/reserva")
public class ReservaServicioController {
    @GetMapping("/listado")
    public String mostrarListado(){
        return "servicios/reserva/listado";
    }
    
}
