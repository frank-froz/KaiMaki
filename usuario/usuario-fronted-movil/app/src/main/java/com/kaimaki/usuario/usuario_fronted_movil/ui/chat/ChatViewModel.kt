package com.kaimaki.usuario.usuario_fronted_movil.ui.chat
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.ChatRepositoryImpl
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val chatRepository = ChatRepositoryImpl()

    fun obtenerIdUsuarioActual(): Long {
        return TokenManager.getUserId(context)
    }

    suspend fun obtenerMensajesConUsuario(receptorId: Long): List<ChatMessage> {
        return try {
            val authHeader = TokenManager.getAuthHeader(context)
            Log.d("ChatViewModel", "AuthHeader obtenido: $authHeader")

            if (authHeader.isNullOrEmpty()) {
                Log.e("ChatViewModel", "AuthHeader es nulo o vacío")
                throw Exception("Token no disponible")
            }

            // Primero obtener todos los chats
            val chats = chatRepository.obtenerMisChats(authHeader)
            Log.d("ChatViewModel", "Chats obtenidos: ${chats.size}")

            // Buscar el chat con el usuario específico
            val chat = chats.find { it.otroUsuarioId == receptorId }

            if (chat != null) {
                Log.d("ChatViewModel", "Chat encontrado con ID: ${chat.chatId}")
                val mensajes = chatRepository.obtenerMensajesDelChat(chat.chatId, authHeader)
                Log.d("ChatViewModel", "Mensajes obtenidos: ${mensajes.size}")
                mensajes
            } else {
                Log.d("ChatViewModel", "No se encontró chat con el usuario $receptorId")
                // Si no existe un chat, devolvemos lista vacía
                emptyList()
            }

        } catch (e: HttpException) {
            Log.e("ChatViewModel", "Error HTTP: ${e.code()} - ${e.message()}")
            Log.e("ChatViewModel", "Response body: ${e.response()?.errorBody()?.string()}")

            when (e.code()) {
                403 -> {
                    Log.e("ChatViewModel", "Error 403: Sin permisos o token inválido")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Sin permisos para acceder al chat", Toast.LENGTH_LONG).show()
                    }
                }
                401 -> {
                    Log.e("ChatViewModel", "Error 401: No autorizado - Token expirado")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Sesión expirada. Inicia sesión nuevamente", Toast.LENGTH_LONG).show()
                    }
                }
                404 -> {
                    Log.e("ChatViewModel", "Error 404: Chat no encontrado")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Chat no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            emptyList()
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error inesperado al obtener mensajes: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al cargar mensajes", Toast.LENGTH_SHORT).show()
            }
            emptyList()
        }
    }

    suspend fun enviarMensaje(receptorId: Long, texto: String): ChatMessage {
        return try {
            val authHeader = TokenManager.getAuthHeader(context)
            val userId = TokenManager.getUserId(context)

            Log.d("ChatViewModel", "=== ENVIANDO MENSAJE ===")
            Log.d("ChatViewModel", "AuthHeader: $authHeader")
            Log.d("ChatViewModel", "UserId actual: $userId")
            Log.d("ChatViewModel", "ReceptorId: $receptorId")
            Log.d("ChatViewModel", "Texto: '$texto'")
            Log.d("ChatViewModel", "Body que se enviará: ${mapOf("texto" to texto)}")

            if (authHeader.isNullOrEmpty()) {
                Log.e("ChatViewModel", "AuthHeader es nulo o vacío para enviar mensaje")
                throw Exception("Token no disponible")
            }

            // Verificar que no se esté enviando a sí mismo
            if (userId == receptorId) {
                Log.e("ChatViewModel", "Error: Intentando enviar mensaje a sí mismo")
                throw Exception("No puedes enviarte mensajes a ti mismo")
            }

            val mensaje = chatRepository.enviarMensaje(receptorId, texto, authHeader)
            Log.d("ChatViewModel", "Mensaje enviado exitosamente: ${mensaje.id}")
            Log.d("ChatViewModel", "========================")
            mensaje

        } catch (e: HttpException) {
            Log.e("ChatViewModel", "Error HTTP al enviar mensaje: ${e.code()} - ${e.message()}")
            Log.e("ChatViewModel", "Response body: ${e.response()?.errorBody()?.string()}")

            when (e.code()) {
                403 -> {
                    Log.e("ChatViewModel", "Error 403: Sin permisos para enviar mensaje")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Sin permisos para enviar mensaje", Toast.LENGTH_LONG).show()
                    }
                    throw Exception("Sin permisos para enviar mensaje")
                }
                401 -> {
                    Log.e("ChatViewModel", "Error 401: Token expirado")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Sesión expirada", Toast.LENGTH_LONG).show()
                    }
                    throw Exception("Sesión expirada")
                }
                400 -> {
                    Log.e("ChatViewModel", "Error 400: Datos inválidos")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error en los datos del mensaje", Toast.LENGTH_SHORT).show()
                    }
                    throw Exception("Datos inválidos")
                }
            }
            throw e
        } catch (e: Exception) {
            Log.e("ChatViewModel", "Error inesperado al enviar mensaje: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al enviar mensaje", Toast.LENGTH_SHORT).show()
            }
            throw e
        }
    }

    // Método adicional para debugging
    fun debugTokenInfo() {
        val token = TokenManager.getToken(context)
        val userId = TokenManager.getUserId(context)
        val authHeader = TokenManager.getAuthHeader(context)

        Log.d("ChatViewModel", "=== DEBUG TOKEN INFO ===")
        Log.d("ChatViewModel", "Token: $token")
        Log.d("ChatViewModel", "UserId: $userId")
        Log.d("ChatViewModel", "AuthHeader: $authHeader")
        Log.d("ChatViewModel", "========================")
    }
}