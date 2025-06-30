package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    Optional<Trabajador> findByUserId(Long userId);

    // Reemplazo del método erróneo por consulta personalizada
    @Query("""
    SELECT t FROM Trabajador t
    JOIN t.oficios tofi
    JOIN tofi.oficio o
    WHERE LOWER(TRIM(o.nombre)) LIKE LOWER(CONCAT('%', :nombre, '%'))
    """)
    List<Trabajador> buscarPorNombreDeOficio(@Param("nombre") String nombre);

    @Query("SELECT t FROM Trabajador t WHERE t.estadoVerificacion.nombre = 'Verificado'")
    List<Trabajador> findTrabajadoresVerificados();
}
