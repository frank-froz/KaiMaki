package com.kaimaki.usuario.usuario_fronted_movil.ui.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.*
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerMensajes: RecyclerView
    private lateinit var editMensaje: EditText
    private lateinit var btnEnviar: ImageButton
    private val chatViewModel: ChatViewModel by viewModels()

    private var receptorId: Long = -1
    private var userIdActual: Long = 0

    private val listaMensajes = mutableListOf<ChatMessage>()
    private lateinit var adapter: MensajeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Recibir datos del intent
        receptorId = intent.getLongExtra("receptorId", -1L)
        val nombreReceptor = intent.getStringExtra("nombre") ?: "Usuario"
        val fotoReceptor = intent.getStringExtra("foto") ?: ""

        Log.d("ChatActivity", "=== INICIO CHAT ACTIVITY ===")
        Log.d("ChatActivity", "ReceptorId recibido: $receptorId")
        Log.d("ChatActivity", "Nombre receptor: $nombreReceptor")

        if (receptorId == -1L) {
            Log.e("ChatActivity", "ReceptorId inválido, cerrando activity")
            Toast.makeText(this, "Error: Usuario no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Verificar token antes de continuar
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Log.e("ChatActivity", "Token no disponible, cerrando activity")
            Toast.makeText(this, "Sesión expirada. Inicia sesión nuevamente", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Debug del token
        chatViewModel.debugTokenInfo()

        // Obtener ID del usuario actual
        userIdActual = chatViewModel.obtenerIdUsuarioActual()
        Log.d("ChatActivity", "ID usuario actual: $userIdActual")

        if (userIdActual == -1L) {
            Log.e("ChatActivity", "ID de usuario actual no válido")
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inicializar vistas
        inicializarVistas()

        // Configurar encabezado del chat
        configurarEncabezado(nombreReceptor, fotoReceptor)

        // Configuración RecyclerView
        configurarRecyclerView()

        // Cargar mensajes
        obtenerMensajes()

        // Configurar envío de mensajes
        configurarEnvioMensajes()
    }

    private fun inicializarVistas() {
        recyclerMensajes = findViewById(R.id.recyclerMensajes)
        editMensaje = findViewById(R.id.editMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
    }

    private fun configurarEncabezado(nombreReceptor: String, fotoReceptor: String) {
        val txtNombreChat = findViewById<TextView>(R.id.txtNombreChat)
        val imgPerfilChat = findViewById<ImageView>(R.id.imgPerfilChat)
        val btnVolverChat = findViewById<ImageView>(R.id.btnVolverChat)

        txtNombreChat.text = nombreReceptor

        if (fotoReceptor.isNotEmpty()) {
            Glide.with(this)
                .load(fotoReceptor)
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .into(imgPerfilChat)
        } else {
            imgPerfilChat.setImageResource(R.drawable.default_avatar)
        }

        btnVolverChat.setOnClickListener {
            Log.d("ChatActivity", "Cerrando chat activity")
            finish()
        }
    }

    private fun configurarRecyclerView() {
        adapter = MensajeAdapter(listaMensajes, userIdActual)
        recyclerMensajes.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true // Los mensajes nuevos aparecen abajo
        }
        recyclerMensajes.adapter = adapter
    }

    private fun configurarEnvioMensajes() {
        btnEnviar.setOnClickListener {
            val texto = editMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                Log.d("ChatActivity", "Intentando enviar mensaje: $texto")
                enviarMensaje(texto)
                editMensaje.setText("")
            } else {
                Log.d("ChatActivity", "Texto vacío, no se envía mensaje")
            }
        }

        // También permitir enviar con Enter (opcional)
        editMensaje.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND) {
                btnEnviar.performClick()
                true
            } else {
                false
            }
        }
    }

    private fun obtenerMensajes() {
        Log.d("ChatActivity", "Iniciando carga de mensajes para receptor: $receptorId")

        lifecycleScope.launch {
            try {
                val mensajes = chatViewModel.obtenerMensajesConUsuario(receptorId)
                Log.d("ChatActivity", "Mensajes obtenidos: ${mensajes.size}")

                listaMensajes.clear()
                listaMensajes.addAll(mensajes)
                adapter.notifyDataSetChanged()

                // Scroll al último mensaje si hay mensajes
                if (listaMensajes.isNotEmpty()) {
                    recyclerMensajes.scrollToPosition(listaMensajes.size - 1)
                }

                Log.d("ChatActivity", "UI actualizada con mensajes")

            } catch (e: Exception) {
                Log.e("ChatActivity", "Error al obtener mensajes: ${e.message}", e)
                Toast.makeText(this@ChatActivity, "Error al cargar mensajes: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun enviarMensaje(texto: String) {
        Log.d("ChatActivity", "=== INICIANDO ENVÍO DE MENSAJE ===")
        Log.d("ChatActivity", "Receptor ID: $receptorId")
        Log.d("ChatActivity", "Usuario actual ID: $userIdActual")
        Log.d("ChatActivity", "Texto: '$texto'")

        // Verificación adicional antes de enviar
        if (receptorId == userIdActual) {
            Log.e("ChatActivity", "Error: Receptor es el mismo que el emisor")
            Toast.makeText(this, "No puedes enviarte mensajes a ti mismo", Toast.LENGTH_LONG).show()
            return
        }

        // Deshabilitar el botón temporalmente para evitar envíos múltiples
        btnEnviar.isEnabled = false

        lifecycleScope.launch {
            try {
                val nuevoMensaje = chatViewModel.enviarMensaje(receptorId, texto)
                Log.d("ChatActivity", "Mensaje enviado exitosamente: ${nuevoMensaje.id}")

                listaMensajes.add(nuevoMensaje)
                adapter.notifyItemInserted(listaMensajes.size - 1)
                recyclerMensajes.scrollToPosition(listaMensajes.size - 1)

                Toast.makeText(this@ChatActivity, "Mensaje enviado", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("ChatActivity", "Error al enviar mensaje: ${e.message}", e)
                Toast.makeText(this@ChatActivity, "Error al enviar: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                // Rehabilitar el botón
                btnEnviar.isEnabled = true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Verificar token al volver a la actividad
        val token = TokenManager.getToken(this)
        if (token.isNullOrEmpty()) {
            Log.e("ChatActivity", "Token perdido durante la actividad")
            Toast.makeText(this, "Sesión perdida", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
