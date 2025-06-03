package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String correo;
    @Column(name = "contrasena", nullable = true)
    private String contrasena;
    private String telefono;

    @Column(name = "rol_id")
    private Integer rolId;

    @Column(name = "estado_id")
    private Integer estadoId;

    // Getters y Setters

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Integer getRolId() { return rolId; }

    public void setRolId(Integer rolId) { this.rolId = rolId; }

    public Integer getEstadoId() { return estadoId; }

    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }
}
