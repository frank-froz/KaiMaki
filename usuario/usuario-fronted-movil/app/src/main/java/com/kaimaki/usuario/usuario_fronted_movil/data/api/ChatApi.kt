package com.kaimaki.usuario.usuario_fronted_movil.data.api

import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatDTO
import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatMessageDTO
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.StartChatRequest
import retrofit2.http.*

interface ChatApi {

    // Obtener todos los chats del usuario autenticado
    @GET("api/chat")
    suspend fun getMisChats(
        @Header("Authorization") authHeader: String
    ): List<ChatDTO>

    // Obtener mensajes de un chat específico por roomId (String)
    @GET("api/chat/{roomId}/messages")
    suspend fun getMensajesDelChat(
        @Path("roomId") roomId: String,
        @Header("Authorization") authHeader: String
    ): List<ChatMessageDTO>

    // Enviar un nuevo mensaje al chat identificado por roomId
    @POST("api/chat/{roomId}/messages")
    @Headers("Content-Type: application/json")
    suspend fun enviarMensaje(
        @Path("roomId") roomId: String,
        @Body body: Map<String, String>,  // body debe tener clave "content"
        @Header("Authorization") authHeader: String
    ): ChatMessageDTO

    // Iniciar o recuperar un chat con otro usuario (por email)
    @POST("api/chat/start")
    @Headers("Content-Type: application/json")
    suspend fun iniciarChat(
        @Body request: StartChatRequest,
        @Header("Authorization") authHeader: String
    ): ChatDTO

}
