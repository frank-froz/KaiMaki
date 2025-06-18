package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    Optional<Trabajador> findByUserId(Long userId);

    @Query("SELECT t FROM Trabajador t WHERE t.estadoVerificacion.nombre = 'Verificado'")
    List<Trabajador> findTrabajadoresVerificados();
}
