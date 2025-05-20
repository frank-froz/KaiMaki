package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "trabajadores")
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String dni;
    private String antecedentes;

    @ManyToOne
    @JoinColumn(name = "verificacion_id")
    private EstadoVerificacion estadoVerificacion;

    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "trabajador")
    private List<TrabajadorOficio> oficios;
}
