package com.kaimaki.usuario.usuario_fronted_movil.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kaimaki.usuario.usuario_fronted_movil.R

class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        val txt = view.findViewById<TextView>(R.id.txtBienvenida)
        val nombre = activity?.intent?.getStringExtra("nombre") ?: "usuario"
        val apellido = activity?.intent?.getStringExtra("apellido") ?: ""
        txt.text = "Bienvenido, $nombre $apellido"

        return view
    }
}
