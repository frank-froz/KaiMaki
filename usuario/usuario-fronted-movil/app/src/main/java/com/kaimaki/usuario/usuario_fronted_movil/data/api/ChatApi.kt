package com.kaimaki.usuario.usuario_fronted_movil.data.api


import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Chat
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import retrofit2.http.*

interface ChatApi {

    @GET("api/chat")
    suspend fun getMisChats(@Header("Authorization") authHeader: String): List<Chat>

    @GET("api/chat/{chatId}/mensajes")
    suspend fun getMensajesDelChat(
        @Path("chatId") chatId: Long,
        @Header("Authorization") authHeader: String
    ): List<ChatMessage>

    @POST("api/chat/{receptorId}/mensaje")
    @Headers("Content-Type: application/json")
    suspend fun enviarMensaje(
        @Path("receptorId") receptorId: Long,
        @Body body: Map<String, String>,
        @Header("Authorization") authHeader: String
    ): ChatMessage
}