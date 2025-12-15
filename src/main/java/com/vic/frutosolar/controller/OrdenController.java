package com.vic.frutosolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vic.frutosolar.model.Orden;
import com.vic.frutosolar.service.OrdenService;

@RestController
@RequestMapping("/api/v1/ordenes")
@CrossOrigin(origins = "*") // Refuerzo de seguridad para React
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @GetMapping
    public List<Orden> listarOrdenes() {
        return ordenService.listarTodas();
    }

    @PostMapping("/generar")
    public ResponseEntity<?> crearOrden(@RequestBody Orden ordenRequest) {
        try {
            Orden nuevaOrden = ordenService.generarOrden(ordenRequest);
            return ResponseEntity.ok(nuevaOrden);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- ENDPOINT PARA ACTUALIZAR ESTADO (PATCH) ---
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        try {
            ordenService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}