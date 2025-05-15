package com.kaimaki.usuario.usuariobackend.repository;


import com.kaimaki.usuario.usuariobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCorreo(String correo);
}