package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.service.TrabajadorService;
import com.kaimaki.usuario.usuariobackend.service.impl.TrabajadorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;


    @GetMapping("/disponibles")
    public ResponseEntity<List<TrabajadorDTO>> listarTrabajadoresDisponibles(){
        //líneas para depuración
        System.out.println(" Solicitud autenticada por: " + SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(" Roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        List<TrabajadorDTO> lista = trabajadorService.obtenerTrabajadoresDisponibles();
        return ResponseEntity.ok(lista);
    }
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorDTO> obtenerPorId(@PathVariable Long id) {
        TrabajadorDTO dto = trabajadorService.obtenerTrabajadorPorId(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }



    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/buscar")
    public ResponseEntity<List<TrabajadorDTO>> buscarPorOficio(@RequestParam String oficio) {
        List<TrabajadorDTO> resultado = trabajadorService.obtenerTrabajadoresPorOficio(oficio);
        return ResponseEntity.ok(resultado);
    }
}
