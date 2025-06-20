package com.kaimaki.usuario.usuario_fronted_movil.data.api

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}


