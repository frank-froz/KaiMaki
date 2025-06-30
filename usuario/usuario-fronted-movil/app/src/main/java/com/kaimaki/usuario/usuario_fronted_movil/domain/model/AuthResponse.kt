package com.kaimaki.usuario.usuario_fronted_movil.domain.model

data class AuthResponse(
    val usuario: Usuario,
    val token: String
)

