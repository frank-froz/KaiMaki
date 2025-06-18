package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.LoginRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.security.JwtService;
import com.kaimaki.usuario.usuariobackend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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
            // Validación de dominio
            if (!dto.getCorreo().endsWith("@tecsup.edu.pe")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Solo se permiten correos @tecsup.edu.pe");
            }

            User nuevoUsuario = userService.registrarUsuario(dto);
            String token = jwtService.generateToken(nuevoUsuario.getCorreo());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", nuevoUsuario);

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
            String token = jwtService.generateToken(usuario.getCorreo());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
        }
    }

}
