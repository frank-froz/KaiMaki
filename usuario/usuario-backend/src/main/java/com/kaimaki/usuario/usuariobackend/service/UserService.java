package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.LoginRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.model.User;


public interface UserService {
    User registrarUsuario(UserRegistroDTO dto) throws Exception;
    User loginUsuario(LoginRequestDTO dto) throws Exception;
    User obtenerUsuarioActual();

}