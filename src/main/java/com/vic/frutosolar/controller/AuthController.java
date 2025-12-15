package com.vic.frutosolar.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vic.frutosolar.model.Usuario;
import com.vic.frutosolar.repository.UsuarioRepository;
import com.vic.frutosolar.security.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
            // Obtener datos reales del usuario para enviarlos al front
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token = jwtUtil.generateToken(username);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario); // Enviamos objeto usuario completo (sin pass)
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        // Asignar rol por defecto si no viene
        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            usuario.setRoles(Collections.singleton("ROLE_CLIENTE"));
        }
        
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}