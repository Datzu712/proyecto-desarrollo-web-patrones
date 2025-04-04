package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "imagenes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String url;
    
    @ManyToOne
    @JoinColumn(name = "cabana_id", nullable = false)
    private Cabana cabana;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Cabana getCabana() {
        return cabana;
    }

    public void setCabana(Cabana cabana) {
        this.cabana = cabana;
    }

    
}
