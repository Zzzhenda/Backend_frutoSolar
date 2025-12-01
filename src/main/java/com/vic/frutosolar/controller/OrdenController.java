package com.vic.frutosolar.controller;

import com.vic.frutosolar.model.DetalleOrden;
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

    // Endpoint REAL para generar órdenes complejas
    @PostMapping("/generar")
    public Orden crearOrden(@RequestBody Orden ordenRequest) {
        Orden nuevaOrden = new Orden();
        nuevaOrden.setCliente(ordenRequest.getCliente());
        nuevaOrden.setTotal(ordenRequest.getTotal());

        // Vinculamos los detalles a la orden padre
        if (ordenRequest.getDetalles() != null) {
            for (DetalleOrden detalle : ordenRequest.getDetalles()) {
                nuevaOrden.addDetalle(detalle);
            }
        }

        return ordenRepository.save(nuevaOrden);
    }
}