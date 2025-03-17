package com.Proyecto.domain;
import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Promociones")
public class Promociones implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name="id_Promociones")
    private long idPromociones;
    private String descripcion;
    private double precio;
    @Column(name="ruta_imagen")
    private String imagen;
    private boolean disponible;

    public Promociones() {
    }

    public Promociones(String descripcion, double precio, boolean disponible) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
    }
    
    
    
}
