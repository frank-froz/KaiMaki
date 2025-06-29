package com.kaimaki.usuario.usuariobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendMessageRequestDTO {

    @NotBlank(message = "El contenido del mensaje es requerido")
    @Size(max = 1000, message = "El mensaje no puede exceder 1000 caracteres")
    private String content;

    // Constructores
    public SendMessageRequestDTO() {
    }

    public SendMessageRequestDTO(String content) {
        this.content = content;
    }

    // Getters y setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
