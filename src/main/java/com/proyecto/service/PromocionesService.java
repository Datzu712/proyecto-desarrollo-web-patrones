/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Proyecto.service;

import com.Proyecto.domain.Promociones;
import java.util.List;

/**
 *
 * @author juani
 */
public interface PromocionesService {
        // Se obtiene un listado de servicios en un List
    public List<Promociones> getPromociones(boolean activos);
    
    // Se obtiene un Servicio, a partir del id de un servicio
    public Promociones getPromociones(Promociones promociones);
    
    // Se inserta un nuevo servicio si el id del servicio esta vacío
    // Se actualiza un servicio si el id del servicio NO esta vacío
    public void save(Promociones promociones);
    
    // Se elimina el categoria que tiene el id pasado por parámetro
    public void delete(Promociones promociones);
}
