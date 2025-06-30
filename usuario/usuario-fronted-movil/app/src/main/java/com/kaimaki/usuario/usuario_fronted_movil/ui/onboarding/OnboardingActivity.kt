package com.kaimaki.usuario.usuario_fronted_movil.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.databinding.ActivityOnboardingBinding
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.OnboardingItem
import com.kaimaki.usuario.usuario_fronted_movil.ui.login.LoginActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lista de pantallas de onboarding
        val onboardingItems = listOf(
            OnboardingItem(
                title = "Bienvenido a Kaimaki",
                description = "Encuentra y contacta profesionales de confianza cerca de ti.",
                imageRes = R.drawable.onboarding1
            ),
            OnboardingItem(
                title = "Solicita Servicios",
                description = "Publica trabajos y recibe ofertas de trabajadores calificados.",
                imageRes = R.drawable.onboarding2
            ),
            OnboardingItem(
                title = "Fácil y Rápido",
                description = "Conecta en segundos con expertos que solucionan tus necesidades.",
                imageRes = R.drawable.onboarding3
            )
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter
        binding.indicator.setViewPager(binding.viewPager)

        binding.btnSiguiente.setOnClickListener {
            val currentIndex = binding.viewPager.currentItem
            if (currentIndex + 1 < onboardingItems.size) {
                binding.viewPager.currentItem += 1
            } else {
                irAlLogin()
            }
        }
    }


    private fun irAlLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
