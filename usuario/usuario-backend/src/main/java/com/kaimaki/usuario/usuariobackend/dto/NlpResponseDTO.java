package com.kaimaki.usuario.usuariobackend.dto;


public class NlpResponseDTO {
    private String oficio;
    private String respuesta;

    public NlpResponseDTO(String oficio, String respuesta) {
        this.oficio = oficio;
        this.respuesta = respuesta;
    }

    public String getOficio() {
        return oficio;
    }

    public String getRespuesta() {
        return respuesta;
    }
}