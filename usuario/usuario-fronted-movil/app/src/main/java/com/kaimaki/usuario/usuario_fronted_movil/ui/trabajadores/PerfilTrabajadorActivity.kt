package com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
import com.kaimaki.usuario.usuario_fronted_movil.ui.chat.ChatActivity
import com.kaimaki.usuario.usuario_fronted_movil.ui.chat.ChatViewModel
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilTrabajadorActivity : AppCompatActivity() {

    private var trabajadorCargado: Trabajador? = null
    private val chatViewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_trabajador)

        // Configuración de la Toolbar con botón de retroceso
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        // Forzar el ícono a negro
        val backIcon = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_back)
        backIcon?.setTint(ContextCompat.getColor(this, android.R.color.black))
        supportActionBar?.setHomeAsUpIndicator(backIcon)

        val trabajadorId = intent.getLongExtra("trabajador_id", -1L)

        if (trabajadorId != -1L) {
            cargarDatosTrabajador(trabajadorId)
        } else {
            Toast.makeText(this, "ID de trabajador no válido", Toast.LENGTH_SHORT).show()
            finish()
        }

        val botonMensaje = findViewById<Button>(R.id.btnMensajePerfil)

        botonMensaje.setOnClickListener {
            trabajadorCargado?.let { trabajador ->

                val token = TokenManager.getAuthHeader(this)
                if (token.isNullOrEmpty()) {
                    Toast.makeText(this, "Sesión expirada", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (trabajador.correo.isNullOrEmpty()) {
                    Toast.makeText(this, "El trabajador no tiene correo válido", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                Log.d("ChatDebug", "Correo del trabajador: ${trabajador.correo}")

                // Iniciar chat en coroutine
                lifecycleScope.launch {
                    try {
                        val chatDTO = chatViewModel.iniciarChatCon(trabajador.correo)

                        val intent = Intent(this@PerfilTrabajadorActivity, ChatActivity::class.java)
                        intent.putExtra("roomId", chatDTO.roomId)
                        intent.putExtra("receptorNombre", trabajador.nombreCompleto)
                        intent.putExtra("receptorFoto", trabajador.fotoPerfil ?: "")
                        startActivity(intent)

                    } catch (e: Exception) {
                        Log.e("ChatInitError", "Error real: ${e.message}", e)
                        Toast.makeText(this@PerfilTrabajadorActivity, "Error al abrir chat: ${e.message}", Toast.LENGTH_LONG).show()
                    }

                }

            } ?: run {
                Toast.makeText(this, "Cargando datos del trabajador...", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun cargarDatosTrabajador(id: Long) {
        val token = "Bearer ${TokenManager.getToken(this)}" // Asegúrate que TokenManager funcione bien

        RetrofitInstance.trabajadorApi.obtenerTrabajadorPorId(id, token)
            .enqueue(object : Callback<Trabajador> {
                override fun onResponse(call: Call<Trabajador>, response: Response<Trabajador>) {
                    if (response.isSuccessful) {
                        val trabajador = response.body()
                        trabajadorCargado = trabajador
                        findViewById<TextView>(R.id.txtNombrePerfil).text = trabajador?.nombreCompleto
                        findViewById<TextView>(R.id.txtOficioPerfil).text =
                            "Oficio(s): ${trabajador?.oficios?.joinToString(", ")}"
                        findViewById<TextView>(R.id.txtDistritoPerfil).text =
                            "Distrito: ${trabajador?.distrito}"
                    } else {
                        Toast.makeText(
                            this@PerfilTrabajadorActivity,
                            "Error al cargar datos",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("API", "Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Trabajador>, t: Throwable) {
                    Toast.makeText(
                        this@PerfilTrabajadorActivity,
                        "Error de red: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("API", "Fallo: ${t.message}")
                }
            })

    }



}



