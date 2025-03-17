package com.Proyecto.controller;

import com.Proyecto.domain.ServicioAdicional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.Proyecto.service.ServicioAdicionalService;


@Controller
@Slf4j
@RequestMapping("/serviciosadicionales")
public class ServicioAdicionalController {
    
    @Autowired
    private ServicioAdicionalService servicioadicionalService;
    
    @GetMapping("/lista")
    public String inicio(Model model){
        var ServiciosAdicionales=servicioadicionalService.getServiciosAdicionales(false);
        model.addAttribute("serviciosadicionales", ServiciosAdicionales);
        model.addAttribute("totalServiciosadicionales", ServiciosAdicionales.size());
        return "serviciosadicionales/lista";
    }
    
    @PostMapping("/reservar")
    public String servicioadicionalReservar(ServicioAdicional servicioAdicional, Model model){
        var servicioReservado = servicioadicionalService.reservarServicio(servicioAdicional.getIdServicioAdicional());
        
        if(servicioReservado != null){
            model.addAttribute("mensaje", "El servicio adicional se reservo con exito.");
            model.addAttribute("servicioReservado", servicioReservado);
        }else{
            model.addAttribute("mensaje", "No se pudo registrar la reserva del servicio.");
        }
        return "/serviciosadicionales/confirmacion";
    }
    
}
