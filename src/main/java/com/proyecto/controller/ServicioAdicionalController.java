package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/servicios")
public class ServicioAdicionalController {
    
    @GetMapping("/listado")
    public String mostrarServicios(Model model){
        return "servicios/listado";
    }
}
