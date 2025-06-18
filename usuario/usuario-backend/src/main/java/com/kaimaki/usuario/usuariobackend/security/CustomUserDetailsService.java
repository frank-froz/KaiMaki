package com.kaimaki.usuario.usuariobackend.security;


import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User usuario = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Obtener el rol desde el objeto Rol
        String rolNombre = switch (usuario.getRol().getId()) {
            case 1 -> "ROLE_CLIENTE";
            case 2 -> "ROLE_TRABAJADOR";
            default -> "ROLE_CLIENTE";
        };

        UserBuilder builder = org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreo())
                .password(usuario.getContrasena() != null ? usuario.getContrasena() : "")
                .roles(rolNombre.replace("ROLE_", "")); // Spring agrega automáticamente el prefijo "ROLE_"

        return builder.build();
    }
}
