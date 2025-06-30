package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import com.kaimaki.usuario.usuariobackend.repository.OficioRepository;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NlpService {

    private final TrabajadorRepository trabajadorRepository;
    private final GeminiService geminiService;
    private final OficioRepository oficioRepository;

    public NlpService(
            TrabajadorRepository trabajadorRepository,
            GeminiService geminiService,
            OficioRepository oficioRepository
    ) {
        this.trabajadorRepository = trabajadorRepository;
        this.geminiService = geminiService;
        this.oficioRepository = oficioRepository;
    }

    public String detectarOficio(String texto) {
        String textoInferido = geminiService.obtenerOficioDesdeGemini(texto).toLowerCase();
        List<String> oficiosExistentes = oficioRepository.obtenerNombresDeOficios();

        for (String oficio : oficiosExistentes) {
            if (textoInferido.contains(oficio)) {
                return oficio;
            }
        }

        return ""; // No se encontró un oficio válido
    }

    public String generarRespuesta(String oficio) {
        if (oficio.isBlank()) {
            return "No se pudo identificar un oficio válido en tu mensaje.";
        }

        List<Trabajador> trabajadores = trabajadorRepository.buscarPorNombreDeOficio(oficio);

        Set<String> ubicaciones = trabajadores.stream()
                .map(trabajador -> {
                    if (trabajador.getUser() != null &&
                            trabajador.getUser().getUbicacion() != null) {
                        return trabajador.getUser().getUbicacion().getDistrito();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (trabajadores.isEmpty()) {
            return "No se encontraron trabajadores disponibles para el oficio de " + oficio + ".";
        }

        return "Hay " + trabajadores.size() + " " + oficio + (trabajadores.size() > 1 ? "s" : "") +
                " disponibles en: " + String.join(", ", ubicaciones) + ".";
    }
}
