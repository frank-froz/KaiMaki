package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.PerfilDTO;
import com.kaimaki.usuario.usuariobackend.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;


    @GetMapping
    public ResponseEntity<PerfilDTO> obtenerPerfilActual(Authentication authentication) {
        String correo = authentication.getName(); // obtenido del token
        PerfilDTO dto = perfilService.obtenerPerfilPorCorreo(correo);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfil(@PathVariable Long id) {
        PerfilDTO dto = perfilService.obtenerPerfil(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<PerfilDTO> actualizarPerfil(@RequestBody PerfilDTO perfilDTO, Authentication authentication) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autenticado");
        }

        System.out.println("Usuario autenticado: " + authentication.getName()); // ¿Muestra el correo?

        String correo = authentication.getName();
        PerfilDTO actualizado = perfilService.actualizarPerfil(correo, perfilDTO);
        return ResponseEntity.ok(actualizado);
    }



}
