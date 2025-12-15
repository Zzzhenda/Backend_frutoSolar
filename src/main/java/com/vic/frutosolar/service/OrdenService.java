// src/main/java/com/vic/frutosolar/service/OrdenService.java
package com.vic.frutosolar.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vic.frutosolar.model.DetalleOrden;
import com.vic.frutosolar.model.Orden;
import com.vic.frutosolar.model.fruta; // Asegúrate de que tu clase se llame 'fruta' (minúscula) o 'Fruta'
import com.vic.frutosolar.repository.FrutaRepository;
import com.vic.frutosolar.repository.OrdenRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private FrutaRepository frutaRepository;

    public List<Orden> listarTodas() {
        return ordenRepository.findAll();
    }

    @Transactional
    public Orden generarOrden(Orden orden) {
        orden.setFecha(LocalDateTime.now());
        orden.setEstado("Pendiente");

        if (orden.getDetalles() != null) {
            for (DetalleOrden detalle : orden.getDetalles()) {
                // Buscamos el producto y validamos existencia
                fruta producto = frutaRepository.findById(detalle.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado ID: " + detalle.getProductoId()));

                // Validamos Stock
                if (producto.getStock() < detalle.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
                }

                // Descontamos stock al crear la orden
                producto.setStock(producto.getStock() - detalle.getCantidad());
                frutaRepository.save(producto);
                
                detalle.setOrden(orden);
            }
        }
        return ordenRepository.save(orden);
    }

    // --- LÓGICA DE CAMBIO DE ESTADO ---
    @Transactional
    public void cambiarEstado(Long id, String nuevoEstado) {
        Orden orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        
        // Si el estado nuevo es "Cancelado" y el pedido NO estaba cancelado antes:
        // Devolvemos el stock a la tienda.
        if ("Cancelado".equals(nuevoEstado) && !"Cancelado".equals(orden.getEstado())) {
            devolverStock(orden);
        }
        
        orden.setEstado(nuevoEstado);
        ordenRepository.save(orden);
    }

    // Método auxiliar para restaurar inventario
    private void devolverStock(Orden orden) {
        for (DetalleOrden detalle : orden.getDetalles()) {
            fruta producto = frutaRepository.findById(detalle.getProductoId()).orElse(null);
            if (producto != null) {
                producto.setStock(producto.getStock() + detalle.getCantidad());
                frutaRepository.save(producto);
            }
        }
    }
}