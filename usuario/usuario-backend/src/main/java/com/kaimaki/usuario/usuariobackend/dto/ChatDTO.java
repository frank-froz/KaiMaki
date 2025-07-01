package com.kaimaki.usuario.usuariobackend.dto;

import com.kaimaki.usuario.usuariobackend.model.Chat;
import com.kaimaki.usuario.usuariobackend.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ChatDTO {
    private Long id;
    private String roomId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ChatParticipantDTO> participants;
    private ChatMessageDTO lastMessage;
    private Long unreadCount;

    // Constructores
    public ChatDTO() {
    }

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.roomId = chat.getRoomId();
        this.createdAt = chat.getCreatedAt();
        this.updatedAt = chat.getUpdatedAt();
        this.participants = chat.getParticipants().stream()
                .map(ChatParticipantDTO::new)
                .collect(Collectors.toList());

        // Último mensaje si existe
        if (!chat.getMessages().isEmpty()) {
            this.lastMessage = new ChatMessageDTO(
                    chat.getMessages().get(chat.getMessages().size() - 1));
        }
    }

    // Método para obtener el otro participante (en chat 1:1)
    public ChatParticipantDTO getOtherParticipant(String currentUserEmail) {
        return participants.stream()
                .filter(p -> !p.getEmail().equals(currentUserEmail))
                .findFirst()
                .orElse(null);
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

    public List<ChatParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ChatParticipantDTO> participants) {
        this.participants = participants;
    }

    public ChatMessageDTO getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatMessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Long unreadCount) {
        this.unreadCount = unreadCount;
    }

    // Clase interna para participantes
    public static class ChatParticipantDTO {
        private Long id;
        private String email;
        private String nombre;
        private String apellido;
        private String fotoPerfil;

        public ChatParticipantDTO() {
        }

        public ChatParticipantDTO(User user) {
            this.id = user.getId();
            this.email = user.getCorreo();
            this.nombre = user.getNombre();
            this.apellido = user.getApellido();
            this.fotoPerfil = user.getFotoPerfil();
        }

        // Getters y setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getFotoPerfil() {
            return fotoPerfil;
        }

        public void setFotoPerfil(String fotoPerfil) {
            this.fotoPerfil = fotoPerfil;
        }

        public String getDisplayName() {
            if (nombre != null && apellido != null) {
                return nombre + " " + apellido;
            } else if (nombre != null) {
                return nombre;
            }
            return email;
        }
    }
}
