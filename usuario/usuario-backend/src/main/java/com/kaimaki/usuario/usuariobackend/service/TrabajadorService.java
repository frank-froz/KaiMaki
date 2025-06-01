package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import com.kaimaki.usuario.usuariobackend.model.Ubicacion;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import com.kaimaki.usuario.usuariobackend.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    // Obtener trabajadores verificados con info de ubicación y oficios
    public List<TrabajadorDTO> obtenerTrabajadoresDisponibles() {
        List<Trabajador> trabajadores = trabajadorRepository.findTrabajadoresVerificados();

        return trabajadores.stream().map(t -> {
            String nombreCompleto = t.getUser().getNombre() + " " + t.getUser().getApellido();

            List<String> oficios = t.getOficios().stream()
                    .map(to -> to.getOficio().getNombre())
                    .collect(Collectors.toList());

            Ubicacion ubicacion = ubicacionRepository.findByUserId(t.getUser().getId());

            return new TrabajadorDTO(
                    t.getId(),
                    nombreCompleto,
                    oficios,
                    ubicacion != null ? ubicacion.getDireccion() : "Sin dirección",
                    ubicacion != null ? ubicacion.getDistrito() : "Sin distrito",
                    ubicacion != null ? ubicacion.getProvincia() : "Sin provincia",
                    ubicacion != null ? ubicacion.getDepartamento() : "Sin departamento"
            );
        }).collect(Collectors.toList());
    }

    // Buscar trabajadores por nombre de oficio (filtrado)
    public List<TrabajadorDTO> obtenerTrabajadoresPorOficio(String nombreOficio) {
        List<Trabajador> trabajadores = trabajadorRepository.findTrabajadoresVerificados();

        return trabajadores.stream()
                .filter(t -> t.getOficios().stream()
                        .anyMatch(to -> to.getOficio().getNombre().equalsIgnoreCase(nombreOficio)))
                .map(t -> {
                    String nombreCompleto = t.getUser().getNombre() + " " + t.getUser().getApellido();

                    List<String> oficios = t.getOficios().stream()
                            .map(to -> to.getOficio().getNombre())
                            .collect(Collectors.toList());

                    Ubicacion ubicacion = ubicacionRepository.findByUserId(t.getUser().getId());

                    return new TrabajadorDTO(
                            t.getId(),
                            nombreCompleto,
                            oficios,
                            ubicacion != null ? ubicacion.getDireccion() : "Sin dirección",
                            ubicacion != null ? ubicacion.getDistrito() : "Sin distrito",
                            ubicacion != null ? ubicacion.getProvincia() : "Sin provincia",
                            ubicacion != null ? ubicacion.getDepartamento() : "Sin departamento"
                    );
                })
                .collect(Collectors.toList());
    }
}
