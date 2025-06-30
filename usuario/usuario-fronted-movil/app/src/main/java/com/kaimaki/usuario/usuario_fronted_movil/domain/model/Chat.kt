package com.kaimaki.usuario.usuario_fronted_movil.domain.model

data class Chat(
    val chatId: Long,
    val otroUsuarioId: Long,
    val otroUsuarioNombre: String,
    val otroUsuarioCorreo: String,
    val otroUsuarioFoto: String?,
    val ultimoMensaje: String?,
    val enviadoEn: String?
)
