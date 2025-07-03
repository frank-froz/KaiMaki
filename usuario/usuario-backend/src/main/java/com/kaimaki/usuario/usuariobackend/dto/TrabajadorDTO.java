package com.kaimaki.usuario.usuariobackend.dto;

import java.util.List;

public class TrabajadorDTO {
    private Long id;
    private Long userId;
    private String nombreCompleto;
    private List<String> oficios;
    private String fotoPerfil;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private String correo;

    // Constructor completo
    public TrabajadorDTO(Long idTrabajador, Long userId, String nombreCompleto, List<String> oficios,
                         String fotoPerfil, String direccion, String distrito, String provincia, String departamento,String correo) {
        this.id = idTrabajador;
        this.userId = userId;
        this.nombreCompleto = nombreCompleto;
        this.oficios = oficios;
        this.fotoPerfil = fotoPerfil;
        this.direccion = direccion;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
        this.correo = correo;
    }


    // Constructor vacío
    public TrabajadorDTO() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public List<String> getOficios() { return oficios; }
    public void setOficios(List<String> oficios) { this.oficios = oficios; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo;}
}
