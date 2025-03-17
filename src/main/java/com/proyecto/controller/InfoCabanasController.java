package com.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cabanas/infoCabanas")
public class InfoCabanasController {
    @GetMapping("/listado")
    public String mostrarListado(Model model){
        return "cabanas/infoCabanas/listado";
    }
}
