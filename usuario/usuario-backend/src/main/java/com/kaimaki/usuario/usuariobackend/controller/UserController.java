package com.kaimaki.usuario.usuariobackend.controller;
import com.kaimaki.usuario.usuariobackend.dto.UserRegistroDTO;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody UserRegistroDTO dto) {
        try {
            User nuevoUsuario = userService.registrarUsuario(dto);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
