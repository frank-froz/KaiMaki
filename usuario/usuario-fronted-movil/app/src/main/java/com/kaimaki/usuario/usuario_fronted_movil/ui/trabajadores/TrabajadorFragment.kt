package com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.databinding.FragmentTrabajadoresBinding

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
class TrabajadorFragment : Fragment() {

    private var _binding: FragmentTrabajadoresBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrabajadorViewModel
    private lateinit var adapter: TrabajadorAdapter
    private var listaCompleta: List<Trabajador> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrabajadoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Inicializar ViewModel
        val factory = TrabajadorViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[TrabajadorViewModel::class.java]

        // Configurar RecyclerView
        binding.recyclerTrabajadores.layoutManager = LinearLayoutManager(requireContext())
        adapter = TrabajadorAdapter { trabajador ->
            val intent = Intent(requireContext(), PerfilTrabajadorActivity::class.java)
            intent.putExtra("trabajador_id", trabajador.id)
            startActivity(intent)
        }
        binding.recyclerTrabajadores.adapter = adapter

        // Observar lista desde ViewModel
        viewModel.trabajadores.observe(viewLifecycleOwner) { lista ->
            listaCompleta = lista
            adapter.submitList(lista)
        }

        // Configurar el SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                val texto = newText?.lowercase()?.trim() ?: ""
                val listaFiltrada = if (texto.isEmpty()) {
                    listaCompleta
                } else {
                    listaCompleta.filter { trabajador ->
                        trabajador.nombreCompleto.lowercase().contains(texto) ||
                                trabajador.oficios.any { it.lowercase().contains(texto) }
                    }
                }
                adapter.submitList(listaFiltrada)
                return true
            }
        })

        // Estilo visual del SearchView
        binding.searchView.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.search_view_border)

        val editText = binding.searchView.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(Color.BLACK)
        editText.setHintTextColor(Color.BLACK)

        binding.searchView.apply {
            isIconified = false  // Mantener expandido
            clearFocus()         // Evita problemas de dibujo

            post {
                val editText = findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)
                editText?.apply {
                    requestFocus()
                    setTextColor(Color.BLACK)
                    setHintTextColor(Color.BLACK)

                    // Asegurar visibilidad inmediata del texto
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }


        // Cargar trabajadores
        viewModel.cargarTrabajadores()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


