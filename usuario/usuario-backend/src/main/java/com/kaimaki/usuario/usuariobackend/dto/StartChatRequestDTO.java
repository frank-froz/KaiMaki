package com.kaimaki.usuario.usuariobackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class StartChatRequestDTO {

    @NotBlank(message = "El email del otro usuario es requerido")
    @Email(message = "Debe ser un email válido")
    private String otherUserEmail;

    // Constructores
    public StartChatRequestDTO() {
    }

    public StartChatRequestDTO(String otherUserEmail) {
        this.otherUserEmail = otherUserEmail;
    }

    // Getters y setters
    public String getOtherUserEmail() {
        return otherUserEmail;
    }

    public void setOtherUserEmail(String otherUserEmail) {
        this.otherUserEmail = otherUserEmail;
    }
}
