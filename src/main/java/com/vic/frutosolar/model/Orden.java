package com.vic.frutosolar.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private Double total;
    private LocalDateTime fecha = LocalDateTime.now();

    // RELACIÓN UNO A MUCHOS
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Permite que se serialice esta parte del JSON
    private List<DetalleOrden> detalles = new ArrayList<>();

    // Método helper para agregar detalles y mantener la relación bidireccional
    public void addDetalle(DetalleOrden detalle) {
        detalles.add(detalle);
        detalle.setOrden(this);
    }
}