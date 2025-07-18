package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.LoginRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.dto.UserResponseDTO;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.security.JwtService;
import com.kaimaki.usuario.usuariobackend.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody UserRegistroDTO dto) {
        try {
            if (!dto.getCorreo().endsWith("@tecsup.edu.pe")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Solo se permiten correos @tecsup.edu.pe");
            }

            User nuevoUsuario = userService.registrarUsuario(dto);
            String rol = nuevoUsuario.getRol().getNombre(); // Obtener el nombre del rol
            String token = jwtService.generateToken(nuevoUsuario.getCorreo(), rol);

            UserResponseDTO userDTO = new UserResponseDTO(nuevoUsuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", userDTO);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            User usuario = userService.loginUsuario(dto);
            String rol = usuario.getRol().getNombre(); // Obtener rol en login también
            String token = jwtService.generateToken(usuario.getCorreo(), rol);

            UserResponseDTO userDTO = new UserResponseDTO(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", userDTO);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
        }
    }
}
