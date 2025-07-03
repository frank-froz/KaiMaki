package com.kaimaki.usuario.usuario_fronted_movil.dto

data class ChatDTO(
    val id: Long,
    val roomId: String,
    val createdAt: String,
    val updatedAt: String,
    val participants: List<ChatParticipantDTO>,
    val lastMessage: ChatMessageDTO?,
    val unreadCount: Long? //
)