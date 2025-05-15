package com.kaimaki.usuario.usuariobackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuarios/registro").permitAll() // permite el endpoint de registro
                        .anyRequest().authenticated() // el resto requiere autenticación
                );

        return http.build();
    }
}
