package com.kaimaki.usuario.usuariobackend.service.impl;

import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.dto.LoginRequestDTO;
import com.kaimaki.usuario.usuariobackend.model.Rol;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.RolRepository;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import com.kaimaki.usuario.usuariobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registrarUsuario(UserRegistroDTO dto) throws Exception {
        if (userRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new Exception("El correo ya está registrado.");
        }

        User user = new User();
        user.setCorreo(dto.getCorreo());
        user.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        user.setNombre("Sin nombre");
        user.setApellido("Sin apellido");
        user.setTelefono(null);
        user.setEstadoId(1); // Activo

        // Asignar el rol cliente (ID fijo = 1)
        Rol rolCliente = rolRepository.findById(1L)
                .orElseThrow(() -> new Exception("Rol CLIENTE no encontrado"));
        user.setRol(rolCliente);

        return userRepository.save(user);
    }

    @Override
    public User loginUsuario(LoginRequestDTO dto) throws Exception {
        User user = userRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getContrasena(), user.getContrasena())) {
            throw new Exception("Contraseña incorrecta");
        }

        if (user.getEstadoId() != 1) {
            throw new Exception("Usuario no activo");
        }

        return user;
    }

    public User obtenerOCrearUsuarioGoogle(String correo) {
        return userRepository.findByCorreo(correo).orElseGet(() -> {
            User nuevo = new User();
            nuevo.setCorreo(correo);
            nuevo.setNombre("Usuario Google");
            nuevo.setApellido("Sin apellido");
            nuevo.setContrasena("");
            nuevo.setEstadoId(1);

            // Asignar el rol cliente por ID
            Rol rolCliente = rolRepository.findById(1L)
                    .orElseThrow(); // Lanza excepción si no está, o puedes manejarlo
            nuevo.setRol(rolCliente);

            return userRepository.save(nuevo);
        });
    }

    @Override
    public User obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        String correo = authentication.getName(); // El nombre de usuario es el correo (según tu login)

        return userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

}

