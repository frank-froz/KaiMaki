package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.dto.LoginRequestDTO;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import com.kaimaki.usuario.usuariobackend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Mejor: inyectar el encoder para no instanciarlo cada vez
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registrarUsuario(UserRegistroDTO dto) throws Exception {
        if (userRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new Exception("El correo ya está registrado.");
        }

        User user = new User();
        user.setCorreo(dto.getCorreo());
        user.setContrasena(passwordEncoder.encode(dto.getContrasena()));

        // Valores por defecto, ideal que los traigas del DTO o se definan mejor
        user.setNombre("Sin nombre");
        user.setApellido("Sin apellido");
        user.setTelefono(null);
        user.setRolId(1);    // rol por defecto, ej: usuario normal
        user.setEstadoId(1); // estado activo

        return userRepository.save(user);
    }

    @Override
    public User loginUsuario(LoginRequestDTO dto) throws Exception {
        User user = userRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getContrasena(), user.getContrasena())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Aquí podrías validar si el usuario está activo o verificado
        if (user.getEstadoId() != 1) {
            throw new Exception("Usuario no activo");
        }

        return user;
    }

}
