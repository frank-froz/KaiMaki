package com.kaimaki.usuario.usuario_fronted_movil.data.api

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.LoginRequest
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthApi {
    @POST("/api/usuarios/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    @POST("api/auth/google")
    suspend fun loginConGoogle(@Body body: Map<String, String>): Response<AuthResponse>
    @POST("api/usuarios/registro")
    suspend fun register(@Body body: Map<String, String>): AuthResponse

    @GET("api/usuarios/perfil")
    suspend fun getPerfil(@Header("Authorization") token: String): Response<Usuario>

    @PUT("api/usuarios/perfil")
    suspend fun actualizarPerfil(
        @Header("Authorization") token: String,
        @Body usuario: Usuario
    ): Response<Void>



}


