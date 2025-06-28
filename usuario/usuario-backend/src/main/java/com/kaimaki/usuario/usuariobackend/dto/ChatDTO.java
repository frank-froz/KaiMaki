package com.kaimaki.usuario.usuariobackend.dto;


import com.kaimaki.usuario.usuariobackend.model.Chat;
import com.kaimaki.usuario.usuariobackend.model.User;

import java.time.LocalDateTime;

public class ChatDTO {

    private Long chatId;

    private Long otroUsuarioId;
    private String otroUsuarioNombre;
    private String otroUsuarioCorreo;
    private String otroUsuarioFoto;

    private String ultimoMensaje;
    private LocalDateTime enviadoEn;

    public ChatDTO(Chat chat, User actual) {
        this.chatId = chat.getId();

        // Detectar el "otro" usuario
        User otro = chat.getParticipants().stream()
                .filter(user -> !user.getId().equals(actual.getId()))
                .findFirst()
                .orElse(null);

        if (otro != null) {
            this.otroUsuarioId = otro.getId();
            this.otroUsuarioNombre = otro.getNombre();
            this.otroUsuarioCorreo = otro.getCorreo();
            this.otroUsuarioFoto = otro.getFotoPerfil();
        }

        // Obtener el último mensaje enviado (opcionalmente podrías filtrar solo los no vacíos)
        chat.getMessages().stream()
                .sorted((a, b) -> b.getSentAt().compareTo(a.getSentAt()))
                .findFirst()
                .ifPresent(mensaje -> {
                    this.ultimoMensaje = mensaje.getText();
                    this.enviadoEn = mensaje.getSentAt();
                });
    }

    // Getters y setters
    public Long getChatId() { return chatId; }

    public Long getOtroUsuarioId() { return otroUsuarioId; }
    public String getOtroUsuarioNombre() { return otroUsuarioNombre; }
    public String getOtroUsuarioCorreo() { return otroUsuarioCorreo; }
    public String getOtroUsuarioFoto() { return otroUsuarioFoto; }

    public String getUltimoMensaje() { return ultimoMensaje; }
    public LocalDateTime getEnviadoEn() { return enviadoEn; }
}