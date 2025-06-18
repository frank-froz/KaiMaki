package com.kaimaki.usuario.usuario_fronted_movil.domain.model

data class AuthResponse(
    val usuario: Usuario,
    val token: String
)

data class Usuario(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String?,
    val rolId: Int,
    val estadoId: Int
)
