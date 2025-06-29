package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.dto.ChatDTO;
import com.kaimaki.usuario.usuariobackend.dto.ChatMessageDTO;

import java.util.List;

public interface ChatService {

    /**
     * Obtener o crear un chat entre dos usuarios
     */
    ChatDTO getOrCreateChat(String email1, String email2);

    /**
     * Obtener chats de un usuario
     */
    List<ChatDTO> getUserChats(String userEmail);

    /**
     * Obtener mensajes de un chat por roomId
     */
    List<ChatMessageDTO> getChatMessages(String roomId, String userEmail);

    /**
     * Enviar un mensaje a un chat
     */
    ChatMessageDTO sendMessage(String roomId, String senderEmail, String content);

    /**
     * Buscar chat por roomId
     */
    ChatDTO getChatByRoomId(String roomId, String userEmail);

    /**
     * Verificar si el usuario puede acceder al chat
     */
    boolean canUserAccessChat(String roomId, String userEmail);
}
