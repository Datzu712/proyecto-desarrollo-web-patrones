
package com.Proyecto.service.impl;
import com.Proyecto.domain.ServicioAdicional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Proyecto.service.ServicioAdicionalService;
import com.Proyecto.dao.ServicioadicionalDao;

@Service
public class ServicioAdicionalServiceImpl {
    @Autowired
    private ServicioadicionalDao serviciosadicionalesDao;
    
    @Transactional(readOnly=true)
    public List<ServicioAdicional> getServiciosAdicionales(boolean activos){
        var lista=serviciosadicionalesDao.findAll();
        if (activos){
            //Elimina todos los elementos donde el isActivo este negado(no este activo)
            lista.removeIf(e -> !e.isDisponible());
        }
        return lista;
    }
    
    @Transactional(readOnly = true)
    public ServicioAdicional getServicioAdicional(ServicioAdicional servicioadicional) {
        return serviciosadicionalesDao.findById(servicioadicional.getIdServicioAdicional()).orElse(null);
    }

    @Transactional
    public void save(ServicioAdicional servicioadicional) {
        serviciosadicionalesDao.save(servicioadicional);
    }

    @Transactional
    public void delete(ServicioAdicional servicioadicional) {
        serviciosadicionalesDao.delete(servicioadicional);
    }
}
