package com.kaimaki.usuario.usuario_fronted_movil.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.kaimaki.usuario.usuario_fronted_movil.R

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Referencias de vista
        val tvSaludo = view.findViewById<TextView>(R.id.tvSaludo)

        // Obtener datos del usuario desde el intent o más adelante desde el ViewModel
        val nombre = activity?.intent?.getStringExtra("nombre") ?: "Usuario"
        val apellido = activity?.intent?.getStringExtra("apellido") ?: ""
        tvSaludo.text = "¡Hola, $nombre $apellido!"


        return view
    }
}
