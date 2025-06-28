package com.kaimaki.usuario.usuario_fronted_movil.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import com.kaimaki.usuario.usuario_fronted_movil.databinding.ActivityRegisterBinding
import com.kaimaki.usuario.usuario_fronted_movil.ui.home.HomeActivity


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels() // Lo crearás en breve

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener {
            val correo = binding.editCorreo.text.toString().trim()
            val contrasena = binding.editContrasena.text.toString()
            val confirmar = binding.editConfirmContrasena.text.toString()

            if (correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (contrasena != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.registrarUsuario(correo, contrasena)
        }

        viewModel.registroResult.observe(this) { result ->
            result.onSuccess { authResponse ->
                // Guardar token
                val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
                prefs.edit().putString("token", authResponse.token).apply()

                // Ir al HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("nombre", authResponse.usuario.nombre)
                intent.putExtra("apellido", authResponse.usuario.apellido)
                startActivity(intent)
                finish()
            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
