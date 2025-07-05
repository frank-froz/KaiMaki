package com.kaimaki.usuario.usuario_fronted_movil.data.repository

import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatDTO
import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatMessageDTO
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.StartChatRequest
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.ChatRepository

class ChatRepositoryImpl : ChatRepository {

    private val api = RetrofitInstance.chatApi

    override suspend fun obtenerMisChats(authHeader: String): List<ChatDTO> {
        return api.getMisChats(authHeader)
    }

    override suspend fun obtenerMensajesDelChat(roomId: String, authHeader: String): List<ChatMessage> {
        val mensajesDTO = api.getMensajesDelChat(roomId, authHeader)
        return mensajesDTO.map { dto ->
            ChatMessage(
                id = dto.id,
                roomId = dto.roomId,
                texto = dto.content,
                enviadoEn = dto.sentAt,
                leido = false,
                emisorId = dto.senderId,
                emisorNombre = dto.senderName,
                emisorCorreo = dto.senderEmail,
                emisorFoto = null,
                receptorId = -1L,
                receptorNombre = "",
                receptorCorreo = "",
                receptorFoto = null
            )
        }
    }

    override suspend fun enviarMensajePorRoomId(roomId: String, texto: String, authHeader: String): ChatMessage {
        val dto = api.enviarMensaje(roomId, mapOf("content" to texto), authHeader)
        return ChatMessage(
            id = dto.id,
            roomId = dto.roomId,
            texto = dto.content,
            enviadoEn = dto.sentAt,
            leido = false,
            emisorId = dto.senderId,
            emisorNombre = dto.senderName,
            emisorCorreo = dto.senderEmail,
            emisorFoto = null,
            receptorId = -1L,
            receptorNombre = "",
            receptorCorreo = "",
            receptorFoto = null
        )
    }

    suspend fun iniciarChatConUsuario(otherUserEmail: String, authHeader: String): ChatDTO {
        return api.iniciarChat(StartChatRequest(otherUserEmail), authHeader)
    }

}