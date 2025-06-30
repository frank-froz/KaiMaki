package com.kaimaki.usuario.usuario_fronted_movil.data.repository

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.UserRepository
import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.LoginRequest


class UserRepositoryImpl : UserRepository {
    override suspend fun login(correo: String, contrasena: String): AuthResponse {
        return RetrofitInstance.authApi.login(LoginRequest(correo, contrasena)).body()
            ?: throw Exception("Credenciales inválidas")
    }

    override suspend fun loginConGoogle(idToken: String): AuthResponse {
        return RetrofitInstance.authApi.loginConGoogle(mapOf("idToken" to idToken)).body()
            ?: throw Exception("Token inválido o error en el backend")
    }

    override suspend fun register(correo: String, contrasena: String): AuthResponse {
        return RetrofitInstance.authApi.register(mapOf("correo" to correo, "contrasena" to contrasena))
    }


}

