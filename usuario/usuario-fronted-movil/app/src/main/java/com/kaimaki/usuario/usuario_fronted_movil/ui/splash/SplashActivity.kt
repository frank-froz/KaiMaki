package com.kaimaki.usuario.usuario_fronted_movil.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.databinding.ActivitySplashBinding
import com.kaimaki.usuario.usuario_fronted_movil.ui.onboarding.OnboardingActivity
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animación moderna de entrada
        iniciarAnimacion()

        // Ir al onboarding luego de 2.5 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }, 2500)
    }

    private fun iniciarAnimacion() {
        val fadeZoom = AnimationUtils.loadAnimation(this, R.anim.fade_in_zoom_slide)
        val vibracion = AnimationUtils.loadAnimation(this, R.anim.shake) // o rotate_bounce

        // Cuando termina el fade/zoom, aplica la vibración o rotación
        fadeZoom.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                binding.logoImage.startAnimation(vibracion)
            }
        })

        binding.logoImage.startAnimation(fadeZoom)
    }

}

