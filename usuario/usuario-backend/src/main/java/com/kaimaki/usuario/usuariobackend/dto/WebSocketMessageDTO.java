package com.kaimaki.usuario.usuariobackend.dto;

/**
 * DTO para mensajes enviados a través de WebSocket
 * Estructura simple que contiene solo los datos necesarios para el chat en
 * tiempo real
 */
public class WebSocketMessageDTO {
    private String senderEmail;
    private String toEmail;
    private String content;
    private String timestamp;

    // Constructores
    public WebSocketMessageDTO() {
    }

    public WebSocketMessageDTO(String senderEmail, String toEmail, String content) {
        this.senderEmail = senderEmail;
        this.toEmail = toEmail;
        this.content = content;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    // Getters y setters
    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WebSocketMessageDTO{" +
                "senderEmail='" + senderEmail + '\'' +
                ", toEmail='" + toEmail + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
