package com.kaimaki.usuario.usuariobackend.service.impl;

import com.kaimaki.usuario.usuariobackend.dto.ChatDTO;
import com.kaimaki.usuario.usuariobackend.dto.ChatMessageDTO;
import com.kaimaki.usuario.usuariobackend.model.Chat;
import com.kaimaki.usuario.usuariobackend.model.ChatMessage;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.ChatMessageRepository;
import com.kaimaki.usuario.usuariobackend.repository.ChatRepository;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import com.kaimaki.usuario.usuariobackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ChatDTO getOrCreateChat(String email1, String email2) {
        System.out.println("[ChatService] Obteniendo/creando chat entre: " + email1 + " y " + email2);

        // Generar roomId consistente
        String roomId = Chat.generateRoomId(email1, email2);
        System.out.println("[ChatService] RoomId generado: " + roomId);

        // Buscar chat existente
        Chat chat = chatRepository.findByRoomId(roomId)
                .orElseGet(() -> {
                    System.out.println("[ChatService] Chat no existe, creando nuevo...");

                    // Crear nuevo chat
                    Chat newChat = new Chat(roomId);

                    // Buscar usuarios
                    User user1 = userRepository.findByCorreo(email1)
                            .orElseThrow(() -> {
                                System.err.println("[ChatService] ❌ Usuario no encontrado: " + email1);
                                return new RuntimeException("Usuario no encontrado: " + email1);
                            });
                    User user2 = userRepository.findByCorreo(email2)
                            .orElseThrow(() -> {
                                System.err.println("[ChatService] ❌ Usuario no encontrado: " + email2);
                                return new RuntimeException("Usuario no encontrado: " + email2);
                            });

                    System.out.println("[ChatService] Usuarios encontrados:");
                    System.out.println("  - User1: " + user1.getNombre() + " (" + user1.getCorreo() + ")");
                    System.out.println("  - User2: " + user2.getNombre() + " (" + user2.getCorreo() + ")");

                    // Agregar participantes
                    newChat.addParticipant(user1);
                    newChat.addParticipant(user2);

                    try {
                        Chat savedChat = chatRepository.save(newChat);
                        System.out.println("[ChatService] ✅ Nuevo chat creado con ID: " + savedChat.getId());
                        return savedChat;
                    } catch (Exception e) {
                        System.err.println("[ChatService] ❌ Error al crear chat: " + e.getMessage());
                        e.printStackTrace();
                        throw new RuntimeException("Error al crear chat: " + e.getMessage(), e);
                    }
                });

        System.out.println("[ChatService] ✅ Chat obtenido/creado exitosamente con ID: " + chat.getId());
        return new ChatDTO(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatDTO> getUserChats(String userEmail) {
        List<Chat> chats = chatRepository.findChatsByUserEmail(userEmail);
        return chats.stream()
                .map(ChatDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getChatMessages(String roomId, String userEmail) {
        Chat chat = chatRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado: " + roomId));

        // Verificar que el usuario puede acceder al chat
        if (!canUserAccessChat(roomId, userEmail)) {
            throw new RuntimeException("Acceso denegado al chat: " + roomId);
        }

        List<ChatMessage> messages = chatMessageRepository.findByChatOrderBySentAtAsc(chat);
        return messages.stream()
                .map(ChatMessageDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageDTO sendMessage(String roomId, String senderEmail, String content) {
        System.out.println("[ChatService] Enviando mensaje:");
        System.out.println("  - RoomId: " + roomId);
        System.out.println("  - Sender: " + senderEmail);
        System.out.println("  - Content: " + content);

        Chat chat = chatRepository.findByRoomId(roomId)
                .orElseThrow(() -> {
                    System.err.println("[ChatService] ❌ Chat no encontrado: " + roomId);
                    return new RuntimeException("Chat no encontrado: " + roomId);
                });

        System.out.println("[ChatService] Chat encontrado con ID: " + chat.getId());

        // Verificar que el usuario puede acceder al chat
        if (!canUserAccessChat(roomId, senderEmail)) {
            System.err.println("[ChatService] ❌ Acceso denegado al chat: " + roomId + " para usuario: " + senderEmail);
            throw new RuntimeException("Acceso denegado al chat: " + roomId);
        }

        System.out.println("[ChatService] Acceso verificado correctamente");

        User sender = userRepository.findByCorreo(senderEmail)
                .orElseThrow(() -> {
                    System.err.println("[ChatService] ❌ Usuario no encontrado: " + senderEmail);
                    return new RuntimeException("Usuario no encontrado: " + senderEmail);
                });

        System.out.println("[ChatService] Usuario encontrado: " + sender.getNombre() + " (" + sender.getCorreo() + ")");

        // Crear y guardar mensaje
        ChatMessage message = new ChatMessage(sender, content, chat);
        chat.addMessage(message);

        System.out.println("[ChatService] Mensaje creado, guardando en base de datos...");

        try {
            ChatMessage savedMessage = chatMessageRepository.save(message);
            System.out.println("[ChatService] ✅ Mensaje guardado con ID: " + savedMessage.getId());

            chatRepository.save(chat); // Actualizar updatedAt del chat
            System.out.println("[ChatService] ✅ Chat actualizado");

            return new ChatMessageDTO(savedMessage);
        } catch (Exception e) {
            System.err.println("[ChatService] ❌ Error al guardar mensaje: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al guardar mensaje: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ChatDTO getChatByRoomId(String roomId, String userEmail) {
        Chat chat = chatRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado: " + roomId));

        // Verificar que el usuario puede acceder al chat
        if (!canUserAccessChat(roomId, userEmail)) {
            throw new RuntimeException("Acceso denegado al chat: " + roomId);
        }

        return new ChatDTO(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canUserAccessChat(String roomId, String userEmail) {
        Chat chat = chatRepository.findByRoomId(roomId).orElse(null);
        if (chat == null) {
            return false;
        }

        return chat.isParticipant(userEmail);
    }
}
