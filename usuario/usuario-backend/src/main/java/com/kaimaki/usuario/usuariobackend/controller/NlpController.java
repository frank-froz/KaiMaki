package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.NlpRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.NlpResponseDTO;
import com.kaimaki.usuario.usuariobackend.dto.NlpRespuestaDTO;
import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.service.NlpService;
import com.kaimaki.usuario.usuariobackend.service.TrabajadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nlp")
@CrossOrigin(origins = "*")
public class NlpController {

    private final NlpService nlpService;
    private final TrabajadorService trabajadorService;

    public NlpController(NlpService nlpService, TrabajadorService trabajadorService) {
        this.nlpService = nlpService;
        this.trabajadorService = trabajadorService;
    }

    @PostMapping("/analizar")
    public NlpRespuestaDTO analizarTexto(@RequestBody NlpRequestDTO request) {
        String oficio = nlpService.detectarOficio(request.getMensaje());
        List<TrabajadorDTO> trabajadores = trabajadorService.obtenerTrabajadoresPorOficio(oficio);

        String respuesta;
        if (trabajadores.isEmpty()) {
            respuesta = "No se encontraron trabajadores para el oficio " + oficio + ".";
        } else {
            respuesta = "Se encontraron " + trabajadores.size() + " trabajadores para el oficio " + oficio + ".";
        }

        return new NlpRespuestaDTO(respuesta, trabajadores, oficio);

    }
}
