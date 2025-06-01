package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.service.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @GetMapping("/disponibles")
    public ResponseEntity<List<TrabajadorDTO>> listarTrabajadoresDisponibles(){
        List<TrabajadorDTO> lista = trabajadorService.obtenerTrabajadoresDisponibles();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TrabajadorDTO>> buscarPorOficio(@RequestParam String oficio) {
        List<TrabajadorDTO> resultado = trabajadorService.obtenerTrabajadoresPorOficio(oficio);
        return ResponseEntity.ok(resultado);
    }
}