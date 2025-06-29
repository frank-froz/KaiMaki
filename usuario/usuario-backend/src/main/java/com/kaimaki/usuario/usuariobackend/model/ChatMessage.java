package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_email", nullable = false)
    private String senderEmail;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // Constructores
    public ChatMessage() {
        this.sentAt = LocalDateTime.now();
    }

    public ChatMessage(String senderEmail, String content, Chat chat) {
        this();
        this.senderEmail = senderEmail;
        this.content = content;
        this.chat = chat;
    }

    public ChatMessage(User sender, String content, Chat chat) {
        this();
        this.sender = sender;
        this.senderEmail = sender.getCorreo();
        this.content = content;
        this.chat = chat;
    } // Getters y setters

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

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
        if (sender != null) {
            this.senderEmail = sender.getCorreo();
        }
    }

    @PrePersist
    protected void onCreate() {
        if (sentAt == null) {
            sentAt = LocalDateTime.now();
        }
    }

    // Métodos de compatibilidad para el frontend existente (deprecated)
    @Deprecated
    public String getFrom() {
        return senderEmail;
    }

    @Deprecated
    public void setFrom(String from) {
        this.senderEmail = from;
    }

    @Deprecated
    public String getText() {
        return content;
    }

    @Deprecated
    public void setText(String text) {
        this.content = text;
    }

    // Método para mantener compatibilidad con 'to' - se puede derivar del chat
    @Deprecated
    public String getTo() {
        if (chat != null && chat.getParticipants().size() == 2) {
            return chat.getParticipants().stream()
                    .filter(user -> !user.getCorreo().equals(senderEmail))
                    .findFirst()
                    .map(User::getCorreo)
                    .orElse(null);
        }
        return null;
    }

    @Deprecated
    public void setTo(String to) {
        // Este método queda vacío para compatibilidad,
        // ya que 'to' se deriva de los participantes del chat
    }
}
