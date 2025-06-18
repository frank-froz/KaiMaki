package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.PerfilDTO;
import com.kaimaki.usuario.usuariobackend.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @PreAuthorize("hasAnyRole('CLIENTE')")
    @GetMapping
    public ResponseEntity<PerfilDTO> obtenerPerfilActual(Authentication authentication) {
        String correo = authentication.getName(); // obtenido del token
        PerfilDTO dto = perfilService.obtenerPerfilPorCorreo(correo);
        return ResponseEntity.ok(dto);
    }


}
