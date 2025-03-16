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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequestMapping("/promociones")
public class PromocionesController {
    
    @Autowired
    private PromocionesService promocionesService;

    @GetMapping("/lista")
    public String lista(Model model) {
        var Promociones = promocionesService.getPromociones(false);
        model.addAttribute("promociones", Promociones);
        model.addAttribute("totalPromociones", Promociones.size());
        return "promociones/lista";
    }
    
    @GetMapping("/guardar/{idPromociones}")
    public String promocionGuardar(Promociones promociones){
        promocionesService.save(promociones);
        return "redirect:/promociones/lista";
    }
    
    @GetMapping("/eliminar/{idPromociones}")
    public String promocionEliminar(Promociones promociones){
        promocionesService.save(promociones);
        return "redirect:/promociones/lista";
    }
    @GetMapping("/editar/{idPromociones}")
    public String promocionEditar(Promociones promociones, Model model){
        promociones = promocionesService.getPromociones(promociones);
        model.addAttribute("promociones", promociones);
        return "/promociones/editar";
    }
    
    //Metodo para adquirir una promocion
     //@PostMapping("/adquirir")
     
}
