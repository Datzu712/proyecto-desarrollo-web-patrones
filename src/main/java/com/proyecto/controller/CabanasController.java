package com.proyecto.controller;

import com.proyecto.domain.Booking;
import com.proyecto.domain.Cabana;
import com.proyecto.domain.Imagen;
import com.proyecto.service.BookingService;
import com.proyecto.service.CabanaService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cabanas")
public class CabanasController {

    private final CabanaService cabanaService;

    public CabanasController(CabanaService cabanaService) {
        this.cabanaService = cabanaService;
    }

    
    @GetMapping
    public ResponseEntity<List<Cabana>> obtenerTodas() {
        return ResponseEntity.ok(cabanaService.obtenerTodasLasCabanas());
    }

    @PostMapping
    public ResponseEntity<Cabana> guardarCabana(@RequestBody Cabana cabana) {
        return ResponseEntity.ok(cabanaService.guardarCabana(cabana));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCabana(@PathVariable Integer id) {
        cabanaService.eliminarCabana(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/imagenes")
    public ResponseEntity<List<Imagen>> obtenerImagenes(@PathVariable Integer id) {
        return ResponseEntity.ok(cabanaService.obtenerImagenesPorCabana(id));
    }

    @PostMapping("/{id}/imagenes")
    public ResponseEntity<Imagen> agregarImagen(@PathVariable Integer id, @RequestParam String url) {
        Imagen imagen = cabanaService.agregarImagenACabana(id, url);
        return imagen != null ? ResponseEntity.ok(imagen) : ResponseEntity.notFound().build();
    }

    @GetMapping("/listado")
    public String mostrarListado(Model model) {
        List<Cabana> cabanas = cabanaService.obtenerTodasLasCabanas();
        model.addAttribute("cabanas", cabanas);
        return "cabanas/listado";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Integer id, Model model) {
        Cabana cabana = cabanaService.obtenerCabanaPorId(id);
        if (cabana == null) {
            return "redirect:/cabanas";
        }
        model.addAttribute("cabana", cabana);
        return "cabanas/infoCabanas/listado";
    }

}
