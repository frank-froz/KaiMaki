package com.kaimaki.usuario.usuariobackend.service.impl;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;
import com.kaimaki.usuario.usuariobackend.model.*;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import com.kaimaki.usuario.usuariobackend.repository.UbicacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TrabajadorServiceImplTest {

    @Mock
    private TrabajadorRepository trabajadorRepository;

    @Mock
    private UbicacionRepository ubicacionRepository;

    @InjectMocks
    private TrabajadorServiceImpl trabajadorService;

    @Test
    void testObtenerTrabajadoresDisponibles() {

        User user = new User();
        user.setId(1L);
        user.setNombre("Carlos");
        user.setApellido("Lopez");
        user.setFotoPerfil("carlos.jpg");


        Oficio oficio = new Oficio();
        oficio.setNombre("Gasfitero");

        TrabajadorOficio to = new TrabajadorOficio();
        to.setOficio(oficio);

        Trabajador trabajador = new Trabajador();
        trabajador.setId(10L);
        trabajador.setUser(user);
        trabajador.setOficios(List.of(to));

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion("Av. Central 123");
        ubicacion.setDistrito("Cercado");
        ubicacion.setProvincia("Lima");
        ubicacion.setDepartamento("Lima");

        // Simular comportamiento
        when(trabajadorRepository.findTrabajadoresVerificados()).thenReturn(List.of(trabajador));
        when(ubicacionRepository.findByUserId(1L)).thenReturn(ubicacion);

        // Ejecutar método
        List<TrabajadorDTO> resultado = trabajadorService.obtenerTrabajadoresDisponibles();

        // Validar resultado
        assertEquals(1, resultado.size());

        TrabajadorDTO dto = resultado.get(0);
        assertEquals(10L, dto.getId());
        assertEquals(1L, dto.getUserId());
        assertEquals("Carlos Lopez", dto.getNombreCompleto());
        assertEquals("carlos.jpg", dto.getFotoPerfil());
        assertEquals(List.of("Gasfitero"), dto.getOficios());
        assertEquals("Av. Central 123", dto.getDireccion());
        assertEquals("Cercado", dto.getDistrito());
        assertEquals("Lima", dto.getProvincia());
        assertEquals("Lima", dto.getDepartamento());

    }
}
