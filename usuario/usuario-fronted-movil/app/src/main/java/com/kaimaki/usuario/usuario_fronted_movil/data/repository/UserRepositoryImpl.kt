package com.kaimaki.usuario.usuario_fronted_movil.data.repository

import android.content.Context
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.UserRepository
import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.LoginRequest
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Usuario
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager

class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val authApi = RetrofitInstance.authApi
    private val tokenManager = TokenManager(context)

    override suspend fun login(correo: String, contrasena: String): AuthResponse {
        return authApi.login(LoginRequest(correo, contrasena)).body()
            ?: throw Exception("Credenciales inválidas")
    }

    override suspend fun loginConGoogle(idToken: String): AuthResponse {
        return authApi.loginConGoogle(mapOf("idToken" to idToken)).body()
            ?: throw Exception("Token inválido")
    }

    override suspend fun register(correo: String, contrasena: String): AuthResponse {
        return authApi.register(mapOf("correo" to correo, "contrasena" to contrasena))
    }

    override suspend fun getPerfil(): Usuario {
        val token = tokenManager.getToken() ?: throw Exception("Token no encontrado")
        val response = authApi.getPerfil("Bearer $token")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error al obtener el perfil")
        }
    }

    override suspend fun actualizarPerfil(usuario: Usuario): Boolean {
        val token = tokenManager.getToken() ?: throw Exception("Token no encontrado")
        val response = authApi.actualizarPerfil("Bearer $token", usuario)
        return response.isSuccessful
    }
}


