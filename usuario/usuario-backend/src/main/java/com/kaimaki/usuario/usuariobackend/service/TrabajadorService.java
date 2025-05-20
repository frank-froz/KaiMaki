package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    public List<TrabajadorDTO> obtenerTrabajadoresDisponibles() {
        List<Trabajador> trabajadores = trabajadorRepository.findTrabajadoresVerificados();

        return trabajadores.stream().map(t ->{
            String nombreCompleto = t.getUser().getNombre() + " " + t.getUser().getApellido();
            String oficio = t.getOficios().isEmpty() ? "Sin oficio" :
                            t.getOficios().get(0).getOficio().getNombre();

            return new TrabajadorDTO(t.getId(), nombreCompleto, oficio);
        }).collect(Collectors.toList());
    }
}
