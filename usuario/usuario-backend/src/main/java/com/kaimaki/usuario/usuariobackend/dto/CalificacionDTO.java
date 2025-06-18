package com.kaimaki.usuario.usuariobackend.dto;

import java.time.LocalDate;

public class CalificacionDTO {
    private int puntuacion;
    private String comentario;
    private LocalDate fecha;

    // Getters y setters


    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
