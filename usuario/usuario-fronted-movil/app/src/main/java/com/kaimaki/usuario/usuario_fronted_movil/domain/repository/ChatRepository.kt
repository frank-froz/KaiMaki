package com.kaimaki.usuario.usuario_fronted_movil.domain.repository

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Chat
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage

interface ChatRepository {
    suspend fun obtenerMisChats(authHeader: String): List<Chat>
    suspend fun obtenerMensajesDelChat(chatId: Long, authHeader: String): List<ChatMessage>
    suspend fun enviarMensaje(receptorId: Long, texto: String, authHeader: String): ChatMessage
}
