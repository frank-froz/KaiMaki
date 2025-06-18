package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

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

    @Column(name = "rol_id", insertable = false, updatable = false)
    private Integer rolId;

    @Column(name = "estado_id")
    private Integer estadoId;

    @Column(columnDefinition = "TEXT")
    private String presentacion;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

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

    public Rol getRol() {
        return rol;
    }

    public void setRolId(Integer rolId) {
        if (this.rol == null) {
            this.rol = new Rol();
        }
        this.rol.setId(rolId);
    }

    public Integer getRolId() {
        return rol != null ? rol.getId() : null;
    }

    public Integer getEstadoId() { return estadoId; }

    public void setEstadoId(Integer estadoId) { this.estadoId = estadoId; }

    public String getPresentacion() { return presentacion; }

    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public String getFotoPerfil() { return fotoPerfil; }

    public void setFotoPerfil(String fotoPerfil) {this.fotoPerfil = fotoPerfil;}
}
