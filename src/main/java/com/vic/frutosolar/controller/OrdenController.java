package com.vic.frutosolar.controller;

import com.vic.frutosolar.model.Orden;
import com.vic.frutosolar.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ordenes")
@CrossOrigin(origins = "*")
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;

    @GetMapping
    public List<Orden> listarOrdenes() {
        return ordenRepository.findAll();
    }
    
    // Endpoint dummy para generar datos rápidos
    @PostMapping("/generar")
    public Orden crearPrueba() {
        Orden o = new Orden();
        o.setCliente("Cliente Prueba");
        o.setTotal(15000.0);
        return ordenRepository.save(o);
    }
}