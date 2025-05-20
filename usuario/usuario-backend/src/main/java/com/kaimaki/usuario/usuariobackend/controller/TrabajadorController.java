package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping("/disponibles")
    public ResponseEntity<List<TrabajadorDTO>> listarTrabajadoresDisponibles(){
        List<TrabajadorDTO> lista = trabajadorService.obtenerTrabajadoresDisponibles();
        return ResponseEntity.ok(lista);
    }
}
