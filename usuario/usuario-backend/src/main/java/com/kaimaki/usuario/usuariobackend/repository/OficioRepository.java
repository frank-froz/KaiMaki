package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Oficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OficioRepository extends JpaRepository<Oficio, Long> {

    @Query("SELECT LOWER(o.nombre) FROM Oficio o")
    List<String> obtenerNombresDeOficios();  // → Para IA, devuelve todos los nombres en minúsculas
}