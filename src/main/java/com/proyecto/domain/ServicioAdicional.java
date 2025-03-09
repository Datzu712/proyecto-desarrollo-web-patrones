package com.Proyecto.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "ServiciosAdicionales")
public class ServicioAdicional implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name="id_ServicioAdicional")
    private long idServicioAdicional;
    private String nombre;
    private String descripcion;
    private double precio;
    //@Column(name="ruta_imagen")
    private String imagen;
    private boolean disponible;
    
    public ServicioAdicional(){
        
    }

    public ServicioAdicional(String nombre, String descripcion, double precio, boolean disponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }

}
