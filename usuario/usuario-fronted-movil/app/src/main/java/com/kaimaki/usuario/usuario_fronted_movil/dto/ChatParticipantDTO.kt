package com.kaimaki.usuario.usuario_fronted_movil.dto

data class ChatParticipantDTO(
    val id: Long,
    val email: String,
    val nombre: String?,
    val apellido: String?,
    val fotoPerfil: String?
)
