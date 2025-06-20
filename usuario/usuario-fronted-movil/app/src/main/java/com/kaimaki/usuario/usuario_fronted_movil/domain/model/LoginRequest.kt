package com.kaimaki.usuario.usuario_fronted_movil.domain.model

data class LoginRequest(
    val correo: String,
    val contrasena: String
)