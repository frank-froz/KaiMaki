package com.kaimaki.usuario.usuario_fronted_movil.websocket

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.LifecycleEvent

class WebSocketManager private constructor() {

    private var stompClient: StompClient? = null
    private var lifecycleDisposable: Disposable? = null
    private var messageDisposable: Disposable? = null

    fun connect(authToken: String, onMessageReceived: (String) -> Unit) {
        val url = "ws://10.200.173.77:8080/ws/websocket"
        Log.d("WebSocket", "🌐 Conectando a $url con token: $authToken")

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)

        val headers = listOf(StompHeader("authorization", "Bearer $authToken"))
        stompClient?.connect(headers)

        lifecycleDisposable = stompClient?.lifecycle()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> {
                        Log.d("WebSocket", "✅ Conexión WebSocket abierta correctamente")
                    }
                    LifecycleEvent.Type.ERROR -> {
                        Log.e("WebSocket", "❌ Error en WebSocket", lifecycleEvent.exception)
                    }
                    LifecycleEvent.Type.CLOSED -> {
                        Log.d("WebSocket", "🔌 WebSocket cerrado")
                    }
                    else -> {
                        Log.d("WebSocket", "📡 Evento: ${lifecycleEvent.type}")
                    }
                }
            }, { error ->
                Log.e("WebSocket", "❌ Error en lifecycle WebSocket", error)
            })

        // Suscribirse a la cola privada
        val destination = "/user/queue/messages"
        Log.d("WebSocket", "📡 Suscribiéndose a $destination")

        messageDisposable = stompClient?.topic(destination)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ stompMessage ->
                Log.d("WebSocket", "📩 Mensaje recibido: ${stompMessage.payload}")
                onMessageReceived(stompMessage.payload)
            }, { error ->
                Log.e("WebSocket", "❌ Error recibiendo mensajes en $destination", error)
            })
    }

    fun disconnect() {
        Log.d("WebSocket", "🔌 Desconectando WebSocket")
        messageDisposable?.dispose()
        lifecycleDisposable?.dispose()
        stompClient?.disconnect()
    }

    companion object {
        val instance: WebSocketManager by lazy { WebSocketManager() }
    }
}
