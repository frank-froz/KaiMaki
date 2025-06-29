package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 500)
    private String roomId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "chat_participants", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatMessage> messages = new ArrayList<>();

    // Constructores
    public Chat() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Chat(String roomId) {
        this();
        this.roomId = roomId;
    }

    // Método estático para generar roomId consistente
    public static String generateRoomId(String email1, String email2) {
        String[] emails = { email1, email2 };
        java.util.Arrays.sort(emails);
        return emails[0] + "_" + emails[1];
    }

    // Método para agregar participante
    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }

    // Método para agregar mensaje
    public void addMessage(ChatMessage message) {
        messages.add(message);
        message.setChat(this);
        this.updatedAt = LocalDateTime.now();
    }

    // Método para verificar si un usuario es participante
    public boolean isParticipant(String email) {
        return participants.stream()
                .anyMatch(user -> user.getCorreo().equals(email));
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
