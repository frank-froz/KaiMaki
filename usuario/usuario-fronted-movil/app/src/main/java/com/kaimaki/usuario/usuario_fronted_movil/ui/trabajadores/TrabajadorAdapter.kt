package com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador

class TrabajadorAdapter(
    private val onItemClick: (Trabajador) -> Unit
) : ListAdapter<Trabajador, TrabajadorAdapter.TrabajadorViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trabajador, parent, false)
        return TrabajadorViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrabajadorViewHolder(
        private val view: View,
        private val onItemClick: (Trabajador) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        private val txtDistrito = view.findViewById<TextView>(R.id.txtUbicacion)
        private val layoutOficios = view.findViewById<LinearLayout>(R.id.layoutOficios)

        fun bind(trabajador: Trabajador) {
            val context: Context = view.context

            txtNombre.text = trabajador.nombreCompleto
            txtDistrito.text = trabajador.distrito

            // Limpiar los chips anteriores
            layoutOficios.removeAllViews()

            // Agregar cada oficio como un chip visual
            trabajador.oficios.forEach { oficio ->
                val chip = TextView(context).apply {
                    text = oficio
                    setPadding(40, 10, 40, 10)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    setTextColor(Color.BLACK)
                    setBackgroundResource(R.drawable.oficio_chip_background)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 20, 0)
                    layoutParams = params
                }
                layoutOficios.addView(chip)
            }

            // Click en toda la tarjeta
            view.setOnClickListener {
                onItemClick(trabajador)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Trabajador>() {
        override fun areItemsTheSame(oldItem: Trabajador, newItem: Trabajador): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Trabajador, newItem: Trabajador): Boolean =
            oldItem == newItem
    }

    // Método opcional para actualizar lista filtrada desde SearchView
    fun actualizarLista(nuevaLista: List<Trabajador>) {
        submitList(nuevaLista)
    }
}

