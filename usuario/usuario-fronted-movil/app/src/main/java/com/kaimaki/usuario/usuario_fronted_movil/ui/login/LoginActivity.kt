package com.kaimaki.usuario.usuario_fronted_movil.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kaimaki.usuario.usuario_fronted_movil.databinding.ActivityLoginBinding
import com.kaimaki.usuario.usuario_fronted_movil.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val correo = binding.editCorreo.text.toString().trim()
            val contrasena = binding.editContrasena.text.toString().trim()
            viewModel.login(correo, contrasena)
        }

        viewModel.authResult.observe(this) { result ->
            result.onSuccess { authResponse ->

                // 🔐 GUARDAR EL TOKEN
                val prefs = getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
                prefs.edit().putString("token", authResponse.token).apply()


                val nombre = authResponse.usuario.nombre
                val apellido = authResponse.usuario.apellido

                Toast.makeText(this, "Bienvenido, $nombre $apellido", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("nombre", nombre)
                intent.putExtra("apellido", apellido)
                startActivity(intent)
                finish()

            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
