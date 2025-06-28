package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.CalificacionDTO;
import com.kaimaki.usuario.usuariobackend.dto.PerfilDTO;
import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import com.kaimaki.usuario.usuariobackend.model.Ubicacion;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.CalificacionRepository;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PerfilService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TrabajadorRepository trabajadorRepo;

    @Autowired
    private CalificacionRepository calificacionRepo;

    /**
     * Obtener perfil por ID (para acceder a perfiles de otros usuarios)
     */
    public PerfilDTO obtenerPerfil(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        PerfilDTO dto = new PerfilDTO();

        Ubicacion ubicacion = user.getUbicacion();

        dto.setId(user.getId());
        dto.setNombre(user.getNombre());
        dto.setApellido(user.getApellido());
        dto.setCorreo(user.getCorreo());

        if (ubicacion != null) {
            dto.setDireccion(ubicacion.getDireccion());
            dto.setDistrito(ubicacion.getDistrito());
            dto.setProvincia(ubicacion.getProvincia());
            dto.setDepartamento(ubicacion.getDepartamento());
        } else {
            dto.setDireccion("No registrada");
            dto.setDistrito("No registrado");
            dto.setProvincia("No registrado");
            dto.setDepartamento("No registrado");
        }

        dto.setPresentacion(user.getPresentacion());
        dto.setFotoPerfil(user.getFotoPerfil());

        // Verificamos si es trabajador
        Optional<Trabajador> trabajadorOpt = trabajadorRepo.findByUserId(userId);
        if (trabajadorOpt.isPresent()) {
            Trabajador trabajador = trabajadorOpt.get();

            // Oficios
            List<String> oficios = trabajador.getOficios()
                    .stream()
                    .map(to -> to.getOficio().getNombre())
                    .collect(Collectors.toList());
            dto.setOficios(oficios);

            // Calificaciones
            List<CalificacionDTO> calificaciones = calificacionRepo
                    .findByEvaluadoId(userId)
                    .stream()
                    .map(calif -> {
                        CalificacionDTO c = new CalificacionDTO();
                        c.setPuntuacion(calif.getPuntuacion());
                        c.setComentario(calif.getComentario());
                        c.setFecha(calif.getFecha().toLocalDate());
                        return c;
                    })
                    .collect(Collectors.toList());
            dto.setCalificaciones(calificaciones);
        }

        return dto;
    }

    /**
     * Obtener perfil del usuario autenticado (por correo extraído del token)
     */
    public PerfilDTO obtenerPerfilPorCorreo(String correo) {
        User user = userRepo.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return obtenerPerfil(user.getId()); // reutiliza lógica existente
    }

    public PerfilDTO actualizarPerfil(String correo, PerfilDTO perfilDTO) {
        User user = userRepo.findByCorreo(correo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Actualiza datos del perfil
        user.setNombre(perfilDTO.getNombre());
        user.setApellido(perfilDTO.getApellido());
        user.setPresentacion(perfilDTO.getPresentacion());
        user.setFotoPerfil(perfilDTO.getFotoPerfil());

        // Manejo de Ubicación
        Ubicacion ubicacion = user.getUbicacion();
        if (ubicacion == null) {
            ubicacion = new Ubicacion();
            ubicacion.setUser(user); // <<<<<< ESTA LÍNEA ES CLAVE
            user.setUbicacion(ubicacion); // establece la relación en ambas direcciones
        }

        ubicacion.setDireccion(perfilDTO.getDireccion());
        ubicacion.setDistrito(perfilDTO.getDistrito());
        ubicacion.setProvincia(perfilDTO.getProvincia());
        ubicacion.setDepartamento(perfilDTO.getDepartamento());

        userRepo.save(user); // Guarda todo en cascada

        return obtenerPerfil(user.getId());
    }


}

