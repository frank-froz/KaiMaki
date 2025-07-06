package com.kaimaki.usuario.usuario_fronted_movil.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import com.kaimaki.usuario.usuario_fronted_movil.dto.ChatMessageDTO
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager
import com.kaimaki.usuario.usuario_fronted_movil.websocket.StompClientManager
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerMensajes: RecyclerView
    private lateinit var editMensaje: EditText
    private lateinit var btnEnviar: ImageButton
    private val chatViewModel: ChatViewModel by viewModels()

    private var roomId: String = ""
    private var userIdActual: Long = 0

    private val listaMensajes = mutableListOf<ChatMessage>()
    private lateinit var adapter: MensajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // 🔐 Obtener datos del intent
        roomId = intent.getStringExtra("roomId") ?: ""
        val nombreReceptor = intent.getStringExtra("receptorNombre") ?: "Usuario"
        val fotoReceptor = intent.getStringExtra("receptorFoto") ?: ""

        if (roomId.isEmpty()) {
            Toast.makeText(this, "Chat no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Verifica token válido
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesión expirada", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        userIdActual = chatViewModel.obtenerIdUsuarioActual()
        if (userIdActual == -1L) {
            Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        inicializarVistas()
        configurarEncabezado(nombreReceptor, fotoReceptor)
        configurarRecyclerView()
        obtenerMensajes()
        configurarEnvioMensajes()
    }

    private fun inicializarVistas() {
        recyclerMensajes = findViewById(R.id.recyclerMensajes)
        editMensaje = findViewById(R.id.editMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
    }

    private fun configurarEncabezado(nombreReceptor: String, fotoReceptor: String) {
        findViewById<TextView>(R.id.txtNombreChat).text = nombreReceptor

        val imgPerfilChat = findViewById<ImageView>(R.id.imgPerfilChat)
        if (fotoReceptor.isNotEmpty()) {
            Glide.with(this)
                .load(fotoReceptor)
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .into(imgPerfilChat)
        } else {
            imgPerfilChat.setImageResource(R.drawable.default_avatar)
        }

        findViewById<ImageView>(R.id.btnVolverChat).setOnClickListener { finish() }
    }

    private fun configurarRecyclerView() {
        adapter = MensajeAdapter(listaMensajes, userIdActual)
        recyclerMensajes.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerMensajes.adapter = adapter
    }

    private fun configurarEnvioMensajes() {
        btnEnviar.setOnClickListener {
            val texto = editMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                enviarMensaje(texto)
                editMensaje.setText("")
            }
        }

        editMensaje.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                btnEnviar.performClick()
                true
            } else false
        }
    }

    private fun obtenerMensajes() {
        lifecycleScope.launch {
            try {
                val mensajes = chatViewModel.obtenerMensajesPorChatId(roomId)
                listaMensajes.clear()
                listaMensajes.addAll(mensajes)
                adapter.notifyDataSetChanged()
                if (listaMensajes.isNotEmpty()) {
                    recyclerMensajes.scrollToPosition(listaMensajes.size - 1)
                }
            } catch (e: Exception) {
                Toast.makeText(this@ChatActivity, "Error al cargar mensajes", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun enviarMensaje(texto: String) {
        if (btnEnviar.isEnabled) {
            btnEnviar.isEnabled = false
            lifecycleScope.launch {
                try {
                    val nuevoMensaje = chatViewModel.enviarMensajePorChatId(roomId, texto)
                    listaMensajes.add(nuevoMensaje)
                    adapter.notifyItemInserted(listaMensajes.size - 1)
                    recyclerMensajes.scrollToPosition(listaMensajes.size - 1)
                } catch (e: Exception) {
                    Toast.makeText(this@ChatActivity, "Error al enviar mensaje", Toast.LENGTH_LONG).show()
                } finally {
                    btnEnviar.isEnabled = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesión perdida", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val token = TokenManager.getToken(this)

        if (!token.isNullOrEmpty()) {
            StompClientManager.connect(token) {}

            StompClientManager.connect(token) {
                //  Suscribirse al canal privado (mensajes que me llegan a mí)
                StompClientManager.subscribeToPrivateQueue { payload ->
                    try {
                        Log.d("WebSocket", "📩 Mensaje privado recibido: $payload")

                        val mensajeDTO = Gson().fromJson(payload, ChatMessageDTO::class.java)

                        val mensaje = ChatMessage(
                            id = mensajeDTO.id,
                            roomId = mensajeDTO.roomId,
                            texto = mensajeDTO.content,
                            enviadoEn = mensajeDTO.sentAt,
                            leido = false,
                            emisorId = mensajeDTO.senderId,
                            emisorNombre = mensajeDTO.senderName,
                            emisorCorreo = mensajeDTO.senderEmail,
                            emisorFoto = null,
                            receptorId = -1L,
                            receptorNombre = "",
                            receptorCorreo = "",
                            receptorFoto = null
                        )

                        runOnUiThread {
                            listaMensajes.add(mensaje)
                            adapter.notifyItemInserted(listaMensajes.size - 1)
                            recyclerMensajes.scrollToPosition(listaMensajes.size - 1)
                        }

                    } catch (e: Exception) {
                        Log.e("WebSocket", "❌ Error al procesar mensaje privado: ${e.message}", e)
                    }
                }

            }

        } else {
            Log.e("WebSocket", "❌ Token nulo o vacío. No se conectará a STOMP.")
        }
    }



    override fun onStop() {
        super.onStop()
        StompClientManager.disconnect()
    }


}