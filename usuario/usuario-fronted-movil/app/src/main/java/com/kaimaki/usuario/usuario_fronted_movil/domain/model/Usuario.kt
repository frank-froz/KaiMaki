package com.kaimaki.usuario.usuario_fronted_movil.domain.model

data class Usuario(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String?,
    val presentacion: String?,
    val direccion: String,
    val distrito: String,
    val provincia: String,
    val departamento: String,
    val fotoPerfil: String?,
    val rolId: Int,
    val estadoId: Int
)
