package com.kaimaki.usuario.usuario_fronted_movil.domain.repository

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Usuario

interface UserRepository {
    suspend fun login(correo: String, contrasena: String): AuthResponse
    suspend fun loginConGoogle(idToken: String): AuthResponse
    suspend fun register(correo: String, contrasena: String): AuthResponse

    //suspend fun getPerfil(): Usuario
    //suspend fun actualizarPerfil(usuario: Usuario): Boolean
}