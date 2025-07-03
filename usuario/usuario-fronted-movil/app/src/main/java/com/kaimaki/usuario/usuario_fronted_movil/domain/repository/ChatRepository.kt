package com.kaimaki.usuario.usuario_fronted_movil.domain.repository

import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatDTO
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage

interface ChatRepository {
    suspend fun obtenerMisChats(authHeader: String): List<ChatDTO>
    suspend fun obtenerMensajesDelChat(roomId: String, authHeader: String): List<ChatMessage>
    suspend fun enviarMensajePorRoomId(roomId: String, texto: String, authHeader: String): ChatMessage
}
