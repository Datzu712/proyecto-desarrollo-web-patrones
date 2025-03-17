package com.Proyecto.service;

import com.Proyecto.domain.ServicioAdicional;
import java.util.List;


public interface ServicioAdicionalService {

    
    // Se obtiene un listado de servicios en un List
    public List<ServicioAdicional> getServiciosAdicionales(boolean activos);
    
    // Se obtiene un Servicio, a partir del id de un servicio
    public ServicioAdicional getServiciosAdicionales(ServicioAdicional servicioadicional);
    
    // Se reserva un servicio basado en el id proporcionado
    public ServicioAdicional reservarServicio(Long id);
    
    // Se inserta un nuevo servicio si el id del servicio esta vacío
    // Se actualiza un servicio si el id del servicio NO esta vacío
    public void save(ServicioAdicional servicioadicional);
    
    // Se elimina el servicio que tiene el id pasado por parámetro
    public void delete(ServicioAdicional servicioadicional);
    
    }

