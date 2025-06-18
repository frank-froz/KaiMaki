package com.kaimaki.usuario.usuariobackend.dto;


import com.kaimaki.usuario.usuariobackend.model.User;

public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    //private Integer rolId;
    private Integer estadoId;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();
        this.correo = user.getCorreo();
        this.telefono = user.getTelefono();
        //this.rolId = user.getRolId();
        this.estadoId = user.getEstadoId();
    }

    // Getters y setters


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(final String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(final String telefono) {
        this.telefono = telefono;
    }



    public Integer getEstadoId() {
        return this.estadoId;
    }

    public void setEstadoId(final Integer estadoId) {
        this.estadoId = estadoId;
    }
}