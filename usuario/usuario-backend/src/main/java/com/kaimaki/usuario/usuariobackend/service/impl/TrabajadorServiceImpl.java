package com.kaimaki.usuario.usuariobackend.service.impl;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import com.kaimaki.usuario.usuariobackend.model.Ubicacion;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import com.kaimaki.usuario.usuariobackend.repository.UbicacionRepository;
import com.kaimaki.usuario.usuariobackend.service.TrabajadorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrabajadorServiceImpl implements TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Override
    public List<TrabajadorDTO> obtenerTrabajadoresDisponibles() {
        List<Trabajador> trabajadores = trabajadorRepository.findTrabajadoresVerificados();
        return trabajadores.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrabajadorDTO obtenerTrabajadorPorId(Long id) {
        Optional<Trabajador> opt = trabajadorRepository.findById(id);
        return opt.map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<TrabajadorDTO> obtenerTrabajadoresPorOficio(String nombreOficio) {
        List<Trabajador> trabajadores = trabajadorRepository.findTrabajadoresVerificados();

        return trabajadores.stream()
                .filter(t -> t.getOficios().stream()
                        .anyMatch(to -> to.getOficio().getNombre().equalsIgnoreCase(nombreOficio)))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // 🔁 Método común para mapear Trabajador a TrabajadorDTO
    private TrabajadorDTO mapToDTO(Trabajador t) {
        User usuario = t.getUser();

        String nombreCompleto = usuario.getNombre() + " " + usuario.getApellido();
        String fotoPerfil = usuario.getFotoPerfil();

        List<String> oficios = t.getOficios().stream()
                .map(to -> to.getOficio().getNombre())
                .collect(Collectors.toList());

        Ubicacion ubicacion = ubicacionRepository.findByUserId(usuario.getId());

        return new TrabajadorDTO(
                t.getId(), // ✅ ID del trabajador
                usuario.getId(), // ✅ ID del usuario asociado
                nombreCompleto,
                oficios,
                fotoPerfil,
                ubicacion != null ? ubicacion.getDireccion() : "Sin dirección",
                ubicacion != null ? ubicacion.getDistrito() : "Sin distrito",
                ubicacion != null ? ubicacion.getProvincia() : "Sin provincia",
                ubicacion != null ? ubicacion.getDepartamento() : "Sin departamento",
                usuario.getCorreo()
        );
    }

}
