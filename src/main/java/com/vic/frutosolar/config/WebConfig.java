package com.vic.frutosolar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas las rutas
                .allowedOriginPatterns("*") // Permite cualquier origen (Frontend React)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Habilitamos PATCH para la edición
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}