package com.vic.frutosolar.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;

    // --- CAMPOS NUEVOS NECESARIOS ---
    // Al agregar estas variables, @Data crea setNombre() y setCorreo()
    private String nombre;
    private String correo;
    
    // Agregamos estos también para evitar errores futuros en el Frontend
    private String telefono;
    private String direccion;
    // --------------------------------

    // ROLES: "ROLE_ADMIN", "ROLE_VENDEDOR", "ROLE_CLIENTE"
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
}