package com.kaimaki.usuario.usuario_fronted_movil.domain.model


data class NlpResponse(
    val respuesta: String,
    val trabajadores: List<Trabajador>
)
