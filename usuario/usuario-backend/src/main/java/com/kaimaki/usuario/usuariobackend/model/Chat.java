package com.kaimaki.usuario.usuariobackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages = new ArrayList<>();

    public Chat() {
    }

    public Chat(final Long id, final List<User> participants, final List<ChatMessage> messages) {
        this.id = id;
        this.participants = participants;
        this.messages = messages;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<User> getParticipants() {
        return this.participants;
    }

    public void setParticipants(final List<User> participants) {
        this.participants = participants;
    }

    public List<ChatMessage> getMessages() {
        return this.messages;
    }

    public void setMessages(final List<ChatMessage> messages) {
        this.messages = messages;
    }

    public void setUser1Id(Long userId) {
    }

    public void setUser2Id(Long trabajadorUserId) {
    }

    public void setCreatedAt(LocalDateTime now) {

    }
}

