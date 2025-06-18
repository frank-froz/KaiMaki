package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "asignaciones")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con solicitud
    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    // Cliente (es un User)
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private User cliente;

    // Trabajador (relación con tabla trabajadores)
    @ManyToOne
    @JoinColumn(name = "trabajador_id")
    private Trabajador trabajador;

    @Column(columnDefinition = "TEXT")
    private String descripcionTrabajo;

    private BigDecimal montoAcordado;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoAsignacion estado;

    private LocalDateTime fechaAsignacion;

    // Getters y setters...


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public User getCliente() {
        return cliente;
    }

    public void setCliente(User cliente) {
        this.cliente = cliente;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public BigDecimal getMontoAcordado() {
        return montoAcordado;
    }

    public void setMontoAcordado(BigDecimal montoAcordado) {
        this.montoAcordado = montoAcordado;
    }

    public EstadoAsignacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsignacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
