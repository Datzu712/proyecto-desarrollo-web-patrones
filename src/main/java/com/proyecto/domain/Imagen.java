package com.proyecto.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "cabin_images")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String image_url;
    
    @ManyToOne
    @JoinColumn(name = "cabin_id", nullable = false)
    private Cabana cabana;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Cabana getCabana() {
        return cabana;
    }

    public void setCabana(Cabana cabana) {
        this.cabana = cabana;
    }

    
}
