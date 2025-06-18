package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.TrabajadorDTO;

import java.util.List;

public interface TrabajadorService {

    List<TrabajadorDTO> obtenerTrabajadoresDisponibles();

    TrabajadorDTO obtenerTrabajadorPorId(Long id);

    List<TrabajadorDTO> obtenerTrabajadoresPorOficio(String nombreOficio);
}
