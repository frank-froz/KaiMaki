package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con asignación
    @ManyToOne
    @JoinColumn(name = "asignacion_id")
    private Asignacion asignacion;

    // Evaluador
    @ManyToOne
    @JoinColumn(name = "evaluador_id")
    private User evaluador;

    // Evaluado (el que recibe la calificación)
    @ManyToOne
    @JoinColumn(name = "evaluado_id")
    private User evaluado;

    private Integer puntuacion;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    private LocalDateTime fecha;

    // Getters y setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    public User getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(User evaluador) {
        this.evaluador = evaluador;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public User getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(User evaluado) {
        this.evaluado = evaluado;
    }
}
