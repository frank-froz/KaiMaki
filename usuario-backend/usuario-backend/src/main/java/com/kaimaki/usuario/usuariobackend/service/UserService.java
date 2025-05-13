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
        user.setNombre(dto.getNombre());
        user.setApellido(dto.getApellido());
        user.setCorreo(dto.getCorreo());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String contrasenaEncriptada = encoder.encode(dto.getContrasena());
        user.setContrasena(contrasenaEncriptada);

        user.setTelefono(dto.getTelefono());
        user.setRolId(dto.getRolId());
        user.setEstadoId(dto.getEstadoId());

        return userRepository.save(user);
    }
}
