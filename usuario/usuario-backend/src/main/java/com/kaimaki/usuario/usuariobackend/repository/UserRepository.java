package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Rol;
import com.kaimaki.usuario.usuariobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCorreo(String correo);

    // Buscar usuarios por lista de correos (útil para chats)
    List<User> findByCorreoIn(List<String> correos);

    // Buscar usuario por correo que contenga el texto (para búsqueda)
    List<User> findByCorreoContainingIgnoreCase(String correo);
}