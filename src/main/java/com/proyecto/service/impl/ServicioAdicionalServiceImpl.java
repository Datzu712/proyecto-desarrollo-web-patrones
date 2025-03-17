package com.Proyecto.service.impl;
import com.Proyecto.domain.ServicioAdicional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Proyecto.service.ServicioAdicionalService;
import com.Proyecto.dao.ServicioAdicionalDao;

@Service
public class ServicioAdicionalServiceImpl implements ServicioAdicionalService {
    @Autowired
    private ServicioAdicionalDao serviciosAdicionalDao;
    
    @Override
    @Transactional(readOnly=true)
    public List<ServicioAdicional> getServiciosAdicionales(boolean activos){
        var lista=serviciosAdicionalDao.findAll();
        if (activos){
            //Elimina todos los elementos donde el isActivo este negado(no este activo)
            lista.removeIf(e -> !e.isDisponible());
        }
        return lista;
    }
    
    @Transactional(readOnly = true)
    public ServicioAdicional getServicioAdicionales(ServicioAdicional servicioadicional) {
        return serviciosAdicionalDao.findById(servicioadicional.getIdServicioAdicional()).orElse(null);
    }

    @Transactional
    @Override
    public void save(ServicioAdicional servicioadicional) {
        serviciosAdicionalDao.save(servicioadicional);
    }

    @Transactional
    @Override
    public void delete(ServicioAdicional servicioadicional) {
        serviciosAdicionalDao.delete(servicioadicional);
    }
    @Override
    public ServicioAdicional reservarServicio(Long id){
       ServicioAdicional servicio = serviciosAdicionalDao.findById(id).orElse(null);
       if(servicio != null && servicio.isDisponible()){
           servicio.setDisponible(false);
           serviciosAdicionalDao.save(servicio);
           return servicio;
       }
       return null;
    }
}
