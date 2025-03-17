package com.proyecto.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    private String nombre;
    private String apellidos;
    private String correo;
    private String residencia;
    private String fechaNacimiento;
    private String password;
    private boolean activo;
    
}