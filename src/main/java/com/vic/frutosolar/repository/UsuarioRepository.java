// src/main/java/com/vic/frutosolar/repository/UsuarioRepository.java
package com.vic.frutosolar.repository;
import com.vic.frutosolar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}