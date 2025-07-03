package com.kaimaki.usuario.usuario_fronted_movil.dto

data class ChatMessageDTO(
    val id: Long,
    val senderEmail: String,
    val senderName: String,
    val senderId: Long,
    val content: String,
    val sentAt: String,
    val roomId: String
)
