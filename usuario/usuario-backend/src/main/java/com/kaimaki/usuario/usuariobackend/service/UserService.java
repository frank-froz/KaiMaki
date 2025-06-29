package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.LoginRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.dto.UserResponseDTO;
import com.kaimaki.usuario.usuariobackend.model.User;

import java.util.List;

public interface UserService {
    User registrarUsuario(UserRegistroDTO dto) throws Exception;
    User loginUsuario(LoginRequestDTO dto) throws Exception;
    List<UserResponseDTO> searchUsersByEmail(String email) throws Exception;
}