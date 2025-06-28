package com.kaimaki.usuario.usuario_fronted_movil.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.ChatMessage

class MensajeAdapter(
    private val mensajes: List<ChatMessage>,
    private val userIdActual: Long
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TIPO_ENVIADO = 1
        private const val TIPO_RECIBIDO = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (mensajes[position].emisorId == userIdActual) TIPO_ENVIADO else TIPO_RECIBIDO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TIPO_ENVIADO) {
            val view = inflater.inflate(R.layout.item_mensaje_enviado, parent, false)
            EnviadoViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_mensaje_recibido, parent, false)
            RecibidoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mensaje = mensajes[position]
        if (holder is EnviadoViewHolder) {
            holder.bind(mensaje)
        } else if (holder is RecibidoViewHolder) {
            holder.bind(mensaje)
        }
    }

    override fun getItemCount(): Int = mensajes.size

    class EnviadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtMensaje: TextView = itemView.findViewById(R.id.txtMensajeEnviado)
        fun bind(mensaje: ChatMessage) {
            txtMensaje.text = mensaje.texto
        }
    }

    class RecibidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtMensaje: TextView = itemView.findViewById(R.id.txtMensajeRecibido)
        fun bind(mensaje: ChatMessage) {
            txtMensaje.text = mensaje.texto
        }
    }
}