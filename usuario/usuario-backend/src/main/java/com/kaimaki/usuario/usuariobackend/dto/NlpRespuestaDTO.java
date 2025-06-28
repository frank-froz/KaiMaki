package com.kaimaki.usuario.usuariobackend.dto;

import java.util.List;
public class NlpRespuestaDTO {

    private String respuesta; // texto para voz
    private List<TrabajadorDTO> trabajadores;
    private String oficio; // oficio detectado

    // Constructor con oficio
    public NlpRespuestaDTO(String respuesta, List<TrabajadorDTO> trabajadores, String oficio) {
        this.respuesta = respuesta;
        this.trabajadores = trabajadores;
        this.oficio = oficio;
    }

    // Getters y setters
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public List<TrabajadorDTO> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<TrabajadorDTO> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }
}
