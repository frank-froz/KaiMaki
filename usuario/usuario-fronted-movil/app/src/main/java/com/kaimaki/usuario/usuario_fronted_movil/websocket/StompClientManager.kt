package com.kaimaki.usuario.usuario_fronted_movil.websocket

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader

object StompClientManager {

    private const val SOCKET_URL = "ws://10.200.173.77:8080/ws"
    private lateinit var stompClient: StompClient
    private val disposables = CompositeDisposable()

    var isConnected = false

    fun connect(authToken: String, onConnected: () -> Unit = {}) {
        Log.d("STOMP", "⏳ Conectando a STOMP con token: Bearer $authToken")

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL)

        val headers = listOf(
            StompHeader("Authorization", "Bearer $authToken")
        )

        stompClient.connect(headers)

        disposables.add(
            stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ event ->
                    when (event.type) {
                        LifecycleEvent.Type.OPENED -> {
                            isConnected = true
                            Log.d("STOMP", "✅ Conexión WebSocket abierta")
                            onConnected()
                        }
                        LifecycleEvent.Type.ERROR -> {
                            Log.e("STOMP", "❌ Error en conexión WebSocket", event.exception)
                        }
                        LifecycleEvent.Type.CLOSED -> {
                            isConnected = false
                            Log.d("STOMP", "🔌 Conexión WebSocket cerrada")
                        }
                        else -> {
                            Log.d("STOMP", "Evento STOMP: ${event.type}")
                        }
                    }
                }, { error ->
                    Log.e("STOMP", "❌ Error en lifecycle STOMP", error)
                })
        )
    }

    fun subscribeToRoom(roomId: String, onMessageReceived: (String) -> Unit) {
        if (!isConnected || !::stompClient.isInitialized) return

        val destination = "/topic/sala.$roomId"
        Log.d("STOMP", "📡 Suscribiéndose a: $destination")

        disposables.add(
            stompClient.topic(destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage ->
                    Log.d("STOMP", "📩 Mensaje de sala recibido: ${topicMessage.payload}")
                    onMessageReceived(topicMessage.payload)
                }, { error ->
                    Log.e("STOMP", "❌ Error al suscribirse a sala $destination", error)
                })
        )
    }

    fun subscribeToPrivateQueue(onMessageReceived: (String) -> Unit) {
        if (!isConnected || !::stompClient.isInitialized) return

        val destination = "/user/queue/messages"
        Log.d("STOMP", "📡 Suscribiéndose a: $destination")

        disposables.add(
            stompClient.topic(destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage ->
                    Log.d("STOMP", "📩 Mensaje privado recibido: ${topicMessage.payload}")
                    onMessageReceived(topicMessage.payload)
                }, { error ->
                    Log.e("STOMP", "❌ Error al suscribirse a $destination", error)
                })
        )
    }

    fun disconnect() {
        if (::stompClient.isInitialized) {
            Log.d("STOMP", "🔌 Desconectando STOMP...")
            stompClient.disconnect()
            disposables.clear()
            isConnected = false
        }
    }
}
