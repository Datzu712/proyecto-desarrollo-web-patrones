package com.dream.restapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "amenities", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    private String icon;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
