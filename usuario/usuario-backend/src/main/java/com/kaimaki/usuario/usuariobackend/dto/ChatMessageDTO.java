package com.kaimaki.usuario.usuariobackend.dto;


import com.kaimaki.usuario.usuariobackend.model.ChatMessage;

import java.time.LocalDateTime;

public class ChatMessageDTO {

    private Long id;
    private String texto;
    private LocalDateTime enviadoEn;
    private boolean leido;

    private Long emisorId;
    private String emisorNombre;
    private String emisorCorreo;
    private String emisorFoto;

    private Long receptorId;
    private String receptorNombre;
    private String receptorCorreo;
    private String receptorFoto;

    public ChatMessageDTO(ChatMessage mensaje) {
        this.id = mensaje.getId();
        this.texto = mensaje.getText();
        this.enviadoEn = mensaje.getSentAt();
        this.leido = mensaje.isLeido();

        // Datos del emisor
        this.emisorId = mensaje.getFrom().getId();
        this.emisorNombre = mensaje.getFrom().getNombre();
        this.emisorCorreo = mensaje.getFrom().getCorreo();
        this.emisorFoto = mensaje.getFrom().getFotoPerfil();

        // Datos del receptor
        this.receptorId = mensaje.getTo().getId();
        this.receptorNombre = mensaje.getTo().getNombre();
        this.receptorCorreo = mensaje.getTo().getCorreo();
        this.receptorFoto = mensaje.getTo().getFotoPerfil();
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public LocalDateTime getEnviadoEn() { return enviadoEn; }
    public boolean isLeido() { return leido; }

    public Long getEmisorId() { return emisorId; }
    public String getEmisorNombre() { return emisorNombre; }
    public String getEmisorCorreo() { return emisorCorreo; }
    public String getEmisorFoto() { return emisorFoto; }

    public Long getReceptorId() { return receptorId; }
    public String getReceptorNombre() { return receptorNombre; }
    public String getReceptorCorreo() { return receptorCorreo; }
    public String getReceptorFoto() { return receptorFoto; }
}