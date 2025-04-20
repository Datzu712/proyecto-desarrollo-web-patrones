package com.proyecto.service;

import com.proyecto.domain.Booking;
import com.proyecto.domain.Cabana;
import com.proyecto.domain.Imagen;
import com.proyecto.repository.CabanaRepository;
import com.proyecto.repository.ImagenRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CabanaService {

    private final CabanaRepository cabanaRepository;
    private final ImagenRepository imagenRepository;

    public CabanaService(CabanaRepository cabanaRepository, ImagenRepository imagenRepository) {
        this.cabanaRepository = cabanaRepository;
        this.imagenRepository = imagenRepository;
    }


    public List<Cabana> obtenerTodasLasCabanas() {
        return cabanaRepository.findAll();
    }

    public Cabana obtenerCabanaPorId(Integer id) {
        return cabanaRepository.findById(id).orElse(null);
    }

    public Cabana guardarCabana(Cabana cabana) {
        return cabanaRepository.save(cabana);
    }

    public void eliminarCabana(Integer id) {
        cabanaRepository.deleteById(id);
    }

    public List<Imagen> obtenerImagenesPorCabana(Integer cabanaId) {
        return imagenRepository.findByCabanaId(cabanaId);
    }

    public Imagen agregarImagenACabana(Integer cabanaId, String url) {
        Optional<Cabana> cabana = cabanaRepository.findById(cabanaId);
        if (cabana.isPresent()) {
            Imagen imagen = new Imagen();
            imagen.setImage_url(url);
            imagen.setCabana(cabana.get());
            return imagenRepository.save(imagen);
        }
        return null;
    }
}
