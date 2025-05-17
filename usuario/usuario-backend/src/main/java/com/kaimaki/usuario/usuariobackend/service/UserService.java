package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registrarUsuario(UserRegistroDTO dto) throws Exception {
        if (userRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new Exception("El correo ya está registrado.");
        }

        User user = new User();
        user.setCorreo(dto.getCorreo());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = encoder.encode(dto.getContrasena());
        user.setContrasena(contrasenaEncriptada);

        // Campos por defecto
        user.setNombre("Sin nombre");
        user.setApellido("Sin apellido");
        user.setTelefono(null);
        user.setRolId(1);    // rol por defecto, ej: usuario normal
        user.setEstadoId(1); // estado activo

        return userRepository.save(user);
    }
}
