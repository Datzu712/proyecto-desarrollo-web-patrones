package com.Proyecto.service.impl;
import com.Proyecto.domain.Promociones;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Proyecto.service.ServicioAdicionalService;
import com.Proyecto.dao.PromocionesDao;
import com.Proyecto.domain.ServicioAdicional;
import com.Proyecto.service.PromocionesService;
import com.Proyecto.dao.ServicioAdicionalDao;

@Service
public class PromocionesServiceImpl implements PromocionesService {
    @Autowired
    private PromocionesDao promocionesDao;
    
    
    @Transactional(readOnly=true)
    public List<Promociones> getPromociones(boolean activos){
        var lista = promocionesDao.findAll();
        if (activos){
            //Elimina todos los elementos donde el isActivo este negado(no este activo)
            lista.removeIf(e -> !e.isDisponible());
        }
        return lista;
    }
    
    @Transactional(readOnly = true)
    public Promociones getPromociones(Promociones promociones){
        return promocionesDao.findById(promociones.getIdPromociones()).orElse(null);
    }
    
    @Transactional
    public void save(Promociones promociones) {
        promocionesDao.save(promociones);
    }

    @Transactional
    public void delete(Promociones promociones) {
        promocionesDao.delete(promociones);
    }
}



    