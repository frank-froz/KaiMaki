package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ubicacion ubicacion;

    public Ubicacion getUbicacion() { return ubicacion; }

    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

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

    //  Relación con Rol
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;
    // Estado como simple ID
    @Column(name = "estado_id")
    private Integer estadoId;

    @ManyToMany(mappedBy = "participants")
    private List<Chat> chats;

    @Column(columnDefinition = "TEXT")
    private String presentacion;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Rol getRol() { return rol; }

    public void setRol(Rol rol) { this.rol = rol; }

    public List<Chat> getChats() {return chats; }

    public void setChats(List<Chat> chats) { this.chats = chats; }

    public Integer getEstadoId() { return estadoId; }

    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }

    public String getPresentacion() { return presentacion; }

    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public String getFotoPerfil() { return fotoPerfil; }

    public void setFotoPerfil(String fotoPerfil) {this.fotoPerfil = fotoPerfil;}
}
