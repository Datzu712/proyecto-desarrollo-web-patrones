package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;



@Entity
@Table(name = "cabanas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cabana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    
    @OneToMany(mappedBy = "cabana",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    
}
