package com.kaimaki.usuario.usuario_fronted_movil.ui.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.NlpRepositoryImpl
import com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores.TrabajadorFragment
import kotlinx.coroutines.launch
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var tts: TextToSpeech
    private lateinit var speechRecognizer: SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Aplicar animación de entrada
        overridePendingTransition(R.anim.fade_in_slide_up, 0)

        // Permiso de micrófono
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }

        // Toolbar y navegación
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        // Menú lateral
        val toggle = androidx.appcompat.app.ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    startActivity(Intent(this, com.kaimaki.usuario.usuario_fronted_movil.ui.login.LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        // TextToSpeech
        tts = TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                tts.language = Locale("es", "PE")
            }
        }

        // Botón de voz
        val fab = findViewById<FloatingActionButton>(R.id.fabVoice)
        fab.setOnClickListener {
            iniciarReconocimientoVoz()
        }

        // Habilitar movimiento del botón
        habilitarMovimientoFAB()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            overridePendingTransition(0, R.anim.slide_out_left)
        }
    }

    private fun iniciarReconocimientoVoz() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-PE")
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onResults(results: Bundle?) {
                val texto = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                texto?.let {
                    Snackbar.make(findViewById(R.id.fabVoice), "Dijiste: $it", Snackbar.LENGTH_LONG).show()
                    enviarTextoAlBackend(it)
                }
            }

            override fun onError(error: Int) {
                Toast.makeText(this@HomeActivity, "Error al reconocer voz", Toast.LENGTH_SHORT).show()
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer.startListening(intent)
    }

    private fun enviarTextoAlBackend(texto: String) {
        val repo = NlpRepositoryImpl()
        lifecycleScope.launch {
            val resultado = repo.analizarTexto(texto)
            resultado.onSuccess { respuesta ->
                tts.speak(respuesta.respuesta, TextToSpeech.QUEUE_FLUSH, null, null)
                Toast.makeText(this@HomeActivity, respuesta.respuesta, Toast.LENGTH_LONG).show()

                if (respuesta.trabajadores.isNotEmpty()) {
                    val fragment = TrabajadorFragment.newInstanceWithTrabajadores(respuesta.trabajadores)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(this@HomeActivity, "No se encontraron trabajadores", Toast.LENGTH_SHORT).show()
                }
            }.onFailure {
                Toast.makeText(this@HomeActivity, "Error al conectar: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun habilitarMovimientoFAB() {
        val fab = findViewById<FloatingActionButton>(R.id.fabVoice)
        var dX = 0f
        var dY = 0f

        fab.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    view.animate()
                        .x(event.rawX + dX)
                        .y(event.rawY + dY)
                        .setDuration(0)
                        .start()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    //  Dispara performClick() para mantener compatibilidad
                    view.performClick()
                    true
                }

                else -> false
            }
        }
    }
        override fun onDestroy() {
            super.onDestroy()
            tts.shutdown()
            if (::speechRecognizer.isInitialized) speechRecognizer.destroy()
        }
}
