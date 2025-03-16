
package com.Proyecto.service;

import com.Proyecto.domain.Promociones;
import java.util.List;

public interface PromocionesService {
        // Se obtiene un listado de servicios en un List
    public List<Promociones> getPromociones(boolean activos);
    
    // Se obtiene un Categoria, a partir del id de un categoria
    public Promociones getPromociones(Promociones promociones);
    // Se reserva un servicio basado en el id proporcionado
    public Promociones adquirirPromocion(Long id);
    
    // Se inserta un nuevo categoria si el id del categoria esta vacío
    // Se actualiza un categoria si el id del categoria NO esta vacío
    public void save(Promociones promociones);
    
    // Se elimina el categoria que tiene el id pasado por parámetro
    public void delete(Promociones promociones);
}
