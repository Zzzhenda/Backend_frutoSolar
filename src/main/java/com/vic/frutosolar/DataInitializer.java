package com.vic.frutosolar;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vic.frutosolar.model.Usuario;
import com.vic.frutosolar.model.fruta;
import com.vic.frutosolar.repository.FrutaRepository;
import com.vic.frutosolar.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FrutaRepository frutaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Crear ADMIN si no existe
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setNombre("Administrador Principal");
            admin.setCorreo("admin@frutosolar.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            
            // Datos opcionales para evitar nulos
            admin.setTelefono("+56900000000");
            admin.setDireccion("Oficina Central");
            
            usuarioRepository.save(admin);
            System.out.println(">>> Usuario ADMIN creado: admin / admin123");
        }

        // 2. Crear FRUTAS DE PRUEBA si la tienda está vacía
        if (frutaRepository.count() == 0) {
            crearFruta("Manzana Royal", "Roja y crujiente", 1500, 50, "Frutas Dulces", "https://santaisabel.vtexassets.com/arquivos/ids/174685-900-900?width=900&height=900&aspect=true");
            crearFruta("Plátano Orgánico", "Rico en potasio", 1200, 30, "Frutas Tropicales", "https://santaisabel.vtexassets.com/arquivos/ids/169527-900-900?width=900&height=900&aspect=true");
            crearFruta("Naranja Valencia", "Jugosa para desayuno", 1800, 40, "Cítricos", "https://santaisabel.vtexassets.com/arquivos/ids/162932-900-900?width=900&height=900&aspect=true");
            System.out.println(">>> Catálogo inicial de frutas creado.");
        }
    }

    private void crearFruta(String nombre, String descripcion, double precio, int stock, String categoria, String imagen) {
        fruta f = new fruta();
        f.setNombre(nombre);
        f.setDescripcion(descripcion);
        f.setPrecio(precio);
        f.setStock(stock);
        f.setCategoria(categoria);
        f.setImagen(imagen);
        f.setOrigen("Chile");
        f.setSostenibilidad("Huella de carbono neutra");
        
      
        f.setReceta("Ideal para jugos, postres o consumo fresco."); 
     
        
        frutaRepository.save(f);
    }
}