package com.proyecto.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
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
    private String location;
    private String status;
    private BigDecimal price;
    
    @OneToMany(mappedBy = "cabana",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    } 
    
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
