package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByEvaluadoId(Long id);
}
