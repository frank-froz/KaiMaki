package com.kaimaki.usuario.usuario_fronted_movil.data.repository

import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Chat
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.ChatRepository

import android.util.Log
import retrofit2.HttpException

class ChatRepositoryImpl : ChatRepository {

    private val api = RetrofitInstance.chatApi

    override suspend fun obtenerMisChats(authHeader: String): List<Chat> {
        Log.d("ChatRepository", "=== OBTENIENDO MIS CHATS ===")
        Log.d("ChatRepository", "URL: /api/chat")
        Log.d("ChatRepository", "AuthHeader: $authHeader")

        return try {
            val result = api.getMisChats(authHeader)
            Log.d("ChatRepository", "Chats obtenidos exitosamente: ${result.size}")
            result
        } catch (e: HttpException) {
            Log.e("ChatRepository", "Error HTTP al obtener chats: ${e.code()}")
            Log.e("ChatRepository", "Response: ${e.response()?.errorBody()?.string()}")
            throw e
        }
    }

    override suspend fun obtenerMensajesDelChat(chatId: Long, authHeader: String): List<ChatMessage> {
        Log.d("ChatRepository", "=== OBTENIENDO MENSAJES DEL CHAT ===")
        Log.d("ChatRepository", "URL: /api/chat/$chatId/mensajes")
        Log.d("ChatRepository", "ChatId: $chatId")
        Log.d("ChatRepository", "AuthHeader: $authHeader")

        return try {
            val result = api.getMensajesDelChat(chatId, authHeader)
            Log.d("ChatRepository", "Mensajes obtenidos exitosamente: ${result.size}")
            result
        } catch (e: HttpException) {
            Log.e("ChatRepository", "Error HTTP al obtener mensajes: ${e.code()}")
            Log.e("ChatRepository", "Response: ${e.response()?.errorBody()?.string()}")
            throw e
        }
    }

    override suspend fun enviarMensaje(receptorId: Long, texto: String, authHeader: String): ChatMessage {
        Log.d("ChatRepository", "=== ENVIANDO MENSAJE ===")
        Log.d("ChatRepository", "URL: /api/chat/$receptorId/mensaje")
        Log.d("ChatRepository", "ReceptorId: $receptorId")
        Log.d("ChatRepository", "AuthHeader: $authHeader")

        val body = mapOf("texto" to texto)
        Log.d("ChatRepository", "Body enviado: $body")

        return try {
            val result = api.enviarMensaje(receptorId, body, authHeader)
            Log.d("ChatRepository", "Mensaje enviado exitosamente")
            Log.d("ChatRepository", "Respuesta: ID=${result.id}, Texto='${result.texto}', EmisorId=${result.emisorId}, ReceptorId=${result.receptorId}")
            result
        } catch (e: HttpException) {
            Log.e("ChatRepository", "Error HTTP al enviar mensaje: ${e.code()}")
            Log.e("ChatRepository", "Error message: ${e.message()}")

            val errorBody = e.response()?.errorBody()?.string()
            Log.e("ChatRepository", "Response error body: $errorBody")

            // Información adicional del request
            val request = e.response()?.raw()?.request()
            Log.e("ChatRepository", "Request URL: ${request?.url()}")
            Log.e("ChatRepository", "Request method: ${request?.method()}")
            Log.e("ChatRepository", "Request headers: ${request?.headers()}")

            throw e
        } catch (e: Exception) {
            Log.e("ChatRepository", "Error inesperado al enviar mensaje: ${e.message}", e)
            throw e
        }
    }
}