package com.kaimaki.usuario.usuario_fronted_movil.ui.chat

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.ChatRepositoryImpl
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatDTO
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

    suspend fun obtenerMensajesPorChatId(chatId: String): List<ChatMessage> {
        val authHeader = TokenManager.getAuthHeader(context)
        if (authHeader.isNullOrEmpty()) throw Exception("Token no disponible")

        return try {
            chatRepository.obtenerMensajesDelChat(chatId, authHeader)
        } catch (e: HttpException) {
            manejarErroresHttp(e, "cargar mensajes")
            emptyList()
        } catch (e: Exception) {
            mostrarToast("Error al cargar mensajes")
            emptyList()
        }
    }

    suspend fun enviarMensajePorChatId(chatId: String, texto: String): ChatMessage {
        val authHeader = TokenManager.getAuthHeader(context)
        if (authHeader.isNullOrEmpty()) throw Exception("Token no disponible")

        return try {
            chatRepository.enviarMensajePorRoomId(chatId, texto, authHeader)
        } catch (e: HttpException) {
            manejarErroresHttp(e, "enviar mensaje")
            throw e
        } catch (e: Exception) {
            mostrarToast("Error al enviar mensaje")
            throw e
        }
    }

    private suspend fun manejarErroresHttp(e: HttpException, accion: String) {
        when (e.code()) {
            401 -> mostrarToast("Sesión expirada")
            403 -> mostrarToast("Sin permisos para $accion")
            404 -> mostrarToast("Chat no encontrado")
            400 -> mostrarToast("Datos inválidos")
            else -> mostrarToast("Error al $accion: ${e.code()}")
        }
    }

    private suspend fun mostrarToast(mensaje: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun iniciarChatCon(email: String): ChatDTO {
        val authHeader = TokenManager.getAuthHeader(getApplication())
            ?: throw Exception("Token no disponible")

        return try {
            chatRepository.iniciarChatConUsuario(email, authHeader)
        } catch (e: HttpException) {
            manejarErroresHttp(e, "iniciar chat")
            throw e
        } catch (e: Exception) {
            mostrarToast("Error al iniciar chat")
            throw e
        }
    }

}
// No se requieren cambios si el repositorio ya hace el mapeo.