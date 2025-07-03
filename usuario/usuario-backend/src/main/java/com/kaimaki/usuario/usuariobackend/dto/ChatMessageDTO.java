package com.kaimaki.usuario.usuariobackend.dto;

import com.kaimaki.usuario.usuariobackend.model.ChatMessage;

import java.time.LocalDateTime;

public class ChatMessageDTO {
    private Long id;
    private String senderEmail;
    private String senderName;
    private Long senderId;
    private String content;
    private LocalDateTime sentAt;
    private String roomId;

    // Constructores
    public ChatMessageDTO() {
    }

    public ChatMessageDTO(ChatMessage message) {
        this.id = message.getId();
        this.senderEmail = message.getSenderEmail();
        this.content = message.getContent();
        this.sentAt = message.getSentAt();
        this.senderId = message.getSender().getId();

        if (message.getSender() != null) {
            this.senderName = message.getSender().getNombre() != null ? message.getSender().getNombre() + " " +
                    (message.getSender().getApellido() != null ? message.getSender().getApellido() : "")
                    : message.getSenderEmail();
        } else {
            this.senderName = message.getSenderEmail();
        }

        if (message.getChat() != null) {
            this.roomId = message.getChat().getRoomId();
        }
    }

    // Métodos de compatibilidad para el frontend existente
    public String getFrom() {
        return senderEmail;
    }


    public String getText() {
        return content;
    }

    public String getTo() {
        // Este se puede derivar del roomId si es necesario
        return null; // Por ahora null, se maneja en el frontend
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Long getSenderId() {return senderId;}
    public void setSenderId(Long senderId) { this.senderId = senderId;}

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
