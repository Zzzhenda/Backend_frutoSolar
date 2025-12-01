// src/main/java/com/vic/frutosolar/model/Usuario.java
package com.vic.frutosolar.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

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

    // ROLES: "ADMIN", "VENDEDOR", "CLIENTE"
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
}