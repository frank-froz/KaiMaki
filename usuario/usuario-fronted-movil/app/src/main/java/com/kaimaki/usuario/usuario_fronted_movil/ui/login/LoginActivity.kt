package com.kaimaki.usuario.usuario_fronted_movil.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.databinding.ActivityLoginBinding
import com.kaimaki.usuario.usuario_fronted_movil.ui.home.HomeActivity
import com.kaimaki.usuario.usuario_fronted_movil.ui.register.RegisterActivity
import com.kaimaki.usuario.usuario_fronted_movil.BuildConfig
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aplicar animación de entrada
        overridePendingTransition(R.anim.fade_in_slide_up, 0)

        //  Inicializar ViewModel con Factory
        val factory = LoginViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        // Iniciar sesión con correo y contraseña
        binding.btnLogin.setOnClickListener {
            val correo = binding.editCorreo.text.toString().trim()
            val contrasena = binding.editContrasena.text.toString().trim()
            viewModel.login(correo, contrasena)
        }

        // Ir a pantalla de registro
        binding.txtIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Configurar Google Sign-In
        configurarGoogleSignIn()
        configurarLauncher()

        binding.btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }

        // Observar resultado de login
        viewModel.authResult.observe(this) { result ->
            result.onSuccess { authResponse ->

                // Guardar token e ID del usuario
                TokenManager.setToken(this, authResponse.token)
                TokenManager.setUserId(this, authResponse.usuario.id)

                val nombre = authResponse.usuario.nombre
                val apellido = authResponse.usuario.apellido
                Toast.makeText(this, "Bienvenido, $nombre $apellido", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, HomeActivity::class.java).apply {
                    putExtra("nombre", nombre)
                    putExtra("apellido", apellido)
                })
                finish()

            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.fade_in_slide_up)
    }

    // Configuración de Google Sign-In con clientId desde BuildConfig
    private fun configurarGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    // Registro del launcher para recibir resultado del intent de Google
    private fun configurarLauncher() {
        googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleGoogleSignInResult(task)
            } else {
                Toast.makeText(this, "Inicio de sesión cancelado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Procesar el resultado del login con Google
    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account?.idToken

            if (idToken != null) {
                viewModel.loginConGoogle(idToken)
            } else {
                Toast.makeText(this, "Token de Google inválido", Toast.LENGTH_SHORT).show()
            }

        } catch (e: ApiException) {
            Toast.makeText(this, "Error al iniciar con Google", Toast.LENGTH_SHORT).show()
        }
    }
}
