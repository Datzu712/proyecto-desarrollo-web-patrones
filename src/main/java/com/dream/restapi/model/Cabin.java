package com.dream.restapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name = "cabins")
public class Cabin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private int capacity;

  private BigDecimal price;

  private String status;

  private String location;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToMany
  @JoinTable(name = "cabin_amenities", joinColumns = @JoinColumn(name = "cabin_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"))
  private Set<Amenity> amenities = new HashSet<>();

  @OneToMany(mappedBy = "cabin", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<CabinImage> images;
}
