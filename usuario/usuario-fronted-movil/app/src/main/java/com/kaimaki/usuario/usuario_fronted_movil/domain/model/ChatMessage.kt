package com.kaimaki.usuario.usuario_fronted_movil.domain.model

data class ChatMessage(
    val id: Long,
    val texto: String,
    val enviadoEn: String,
    val leido: Boolean,
    val emisorId: Long,
    val emisorNombre: String,
    val emisorCorreo: String,
    val emisorFoto: String?,
    val receptorId: Long,
    val receptorNombre: String,
    val receptorCorreo: String,
    val receptorFoto: String?
)