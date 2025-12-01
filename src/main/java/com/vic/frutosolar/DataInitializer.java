// src/main/java/com/vic/frutosolar/DataInitializer.java
package com.vic.frutosolar;

import com.vic.frutosolar.model.Usuario;
import com.vic.frutosolar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo crear si no existe
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña encriptada
            admin.setRoles(Set.of("ROLE_ADMIN"));
            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN creado: admin / admin123");
        }
    }
}