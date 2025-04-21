package com.dream.restapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "cabin_images")
public class CabinImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cabin_id", nullable = false)
    @JsonBackReference
    private Cabin cabin;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_main")
    private Boolean isMain = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Lob
    @Column(name = "image_blob")
    private byte[] imageBlob;
}
