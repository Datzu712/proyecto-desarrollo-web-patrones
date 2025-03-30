package com.dream.restapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.dream.restapi.utils.JsonUtil;

import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "password_hash")
    private String passwordHash;
    
    private String role;
    private String phone;
    private String address;
    private String dni;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String toJSON() {
        try {
            return JsonUtil.toJson(this, "passwordHash");
        } catch (Exception e) {
            return "{ \"error\": \"Failed to convert User to JSON\" }";
        }
    }
}
