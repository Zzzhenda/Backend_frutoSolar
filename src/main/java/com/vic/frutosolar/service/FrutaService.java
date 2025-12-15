package com.vic.frutosolar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante

import com.vic.frutosolar.model.fruta;
import com.vic.frutosolar.repository.FrutaRepository;

@Service
public class FrutaService {

    @Autowired
    private FrutaRepository frutaRepository;

    @Transactional
    public fruta guardarFruta(fruta fruta) {
        return frutaRepository.save(fruta);
    }

    public List<fruta> listarFrutas() {
        return frutaRepository.findAll();
    }

    public Optional<fruta> obtenerFrutaPorId(Long id) {
        return frutaRepository.findById(id);
    }

    @Transactional
    public fruta actualizarFruta(Long id, fruta frutaDetails) {
        fruta existente = frutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La fruta con id " + id + " no existe"));
        
        // Actualizamos campos
        existente.setNombre(frutaDetails.getNombre());
        existente.setPrecio(frutaDetails.getPrecio());
        existente.setStock(frutaDetails.getStock());
        existente.setCategoria(frutaDetails.getCategoria());
        existente.setImagen(frutaDetails.getImagen());
        existente.setDescripcion(frutaDetails.getDescripcion());
        existente.setOrigen(frutaDetails.getOrigen());
        existente.setSostenibilidad(frutaDetails.getSostenibilidad());
        existente.setReceta(frutaDetails.getReceta());

        return frutaRepository.save(existente);
    }

    @Transactional
    public void eliminarFruta(Long id) {
        frutaRepository.deleteById(id);
    }
}