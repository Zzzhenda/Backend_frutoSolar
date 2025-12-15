package com.vic.frutosolar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping; // Asegúrate que tu modelo se llame así
import org.springframework.web.bind.annotation.GetMapping; // O tu Service
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vic.frutosolar.model.Usuario;
import com.vic.frutosolar.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "*") // Permite que React se conecte
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    
    // Endpoint para eliminar (necesario para tu botón de borrar)
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}