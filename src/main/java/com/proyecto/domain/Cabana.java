package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;



@Entity
@Table(name = "cabins")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cabana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "description")
    private String descripcion;
    
    @OneToMany(mappedBy = "cabana",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
