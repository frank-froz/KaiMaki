package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    private Integer id;

    private String nombre;

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
}
