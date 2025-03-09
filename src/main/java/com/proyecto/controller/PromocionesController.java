package com.Proyecto.controller;

import com.Proyecto.domain.Promociones;
import com.Proyecto.service.PromocionesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
@RequestMapping("/promociones")
public class PromocionesController {
    
    @Autowired
    private PromocionesService promocionesService;
    @GetMapping("/lista")
    public String lista(Model model){
        var Promociones = promocionesService.getPromociones(false);
        model.addAttribute("promociones", Promociones);
        model.addAttribute("totalPromociones", Promociones.size());
        return "promociones/lista";
        
    
}
}
