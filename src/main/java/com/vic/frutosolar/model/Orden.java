// src/main/java/com/vic/frutosolar/model/Orden.java
package com.vic.frutosolar.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private Double total;
    
    // --- CAMPO NUEVO QUE FALTABA ---
    private String estado; 
    // -------------------------------

    private LocalDateTime fecha = LocalDateTime.now();

    // RELACIÓN UNO A MUCHOS
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleOrden> detalles = new ArrayList<>();

    // Método helper para agregar detalles
    public void addDetalle(DetalleOrden detalle) {
        detalles.add(detalle);
        detalle.setOrden(this);
    }
}