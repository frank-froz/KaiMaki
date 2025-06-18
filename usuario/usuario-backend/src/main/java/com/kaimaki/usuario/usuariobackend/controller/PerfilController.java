package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.PerfilDTO;
import com.kaimaki.usuario.usuariobackend.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> obtenerPerfil(@PathVariable Long id) {
        PerfilDTO dto = perfilService.obtenerPerfil(id);
        return ResponseEntity.ok(dto);
    }
}
