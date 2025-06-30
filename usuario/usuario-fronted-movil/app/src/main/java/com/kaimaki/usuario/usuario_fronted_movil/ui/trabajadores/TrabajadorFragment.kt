package com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaimaki.usuario.usuario_fronted_movil.R
import com.kaimaki.usuario.usuario_fronted_movil.databinding.FragmentTrabajadoresBinding
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
import java.util.Locale

class TrabajadorFragment : Fragment() {

    private var _binding: FragmentTrabajadoresBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TrabajadorAdapter
    private var listaCompleta: List<Trabajador> = emptyList()
    private lateinit var textToSpeech: TextToSpeech

    // ViewModel para manejar la carga de datos
    private lateinit var viewModel: TrabajadorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrabajadoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        setupTextToSpeech()
        loadTrabajadores()
        setupSearchView()
    }

    private fun setupViewModel() {
        val factory = TrabajadorViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[TrabajadorViewModel::class.java]
    }

    private fun setupRecyclerView() {
        binding.recyclerTrabajadores.layoutManager = LinearLayoutManager(requireContext())
        adapter = TrabajadorAdapter { trabajador ->
            navigateToPerfilTrabajador(trabajador)
        }
        binding.recyclerTrabajadores.adapter = adapter
    }

    private fun navigateToPerfilTrabajador(trabajador: Trabajador) {
        try {
            val intent = Intent(requireContext(), PerfilTrabajadorActivity::class.java).apply {
                putExtra("trabajador_id", trabajador.id)
                putExtra("trabajador_nombre", trabajador.nombreCompleto)
                putExtra("trabajador_oficios", ArrayList(trabajador.oficios))
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("TrabajadorFragment", "Error al navegar al perfil: ${e.message}")
        }
    }

    private fun loadTrabajadores() {
        // Verificar si se pasaron trabajadores como argumentos (desde IA)
        val trabajadoresArg = arguments?.getParcelableArrayList<Trabajador>(ARG_TRABAJADORES)

        if (trabajadoresArg != null && trabajadoresArg.isNotEmpty()) {
            // Trabajadores filtrados por IA
            Log.d("TrabajadorFragment", "Cargando ${trabajadoresArg.size} trabajadores filtrados por IA")
            listaCompleta = trabajadoresArg
            adapter.submitList(listaCompleta)

            // Opcional: Leer la lista con TTS
            speakWorkersList(trabajadoresArg)
        } else {
            // Cargar todos los trabajadores manualmente
            Log.d("TrabajadorFragment", "Cargando todos los trabajadores")
            observeAllTrabajadores()
        }
    }

    private fun observeAllTrabajadores() {
        viewModel.trabajadores.observe(viewLifecycleOwner) { lista ->
            if (lista != null && lista.isNotEmpty()) {
                Log.d("TrabajadorFragment", "Recibidos ${lista.size} trabajadores del ViewModel")
                listaCompleta = lista
                adapter.submitList(lista)
            } else {
                Log.w("TrabajadorFragment", "Lista de trabajadores vacía o nula")
                // Mostrar mensaje al usuario si es necesario
                adapter.submitList(emptyList())
            }
        }

        // Si tu ViewModel tiene manejo de errores, descomenta esto:
        // viewModel.errorMessage?.observe(viewLifecycleOwner) { errorMsg ->
        //     if (!errorMsg.isNullOrEmpty()) {
        //         Log.e("TrabajadorFragment", "Error al cargar trabajadores: $errorMsg")
        //         // Mostrar Snackbar o Toast al usuario
        //     }
        // }

        try {
            viewModel.cargarTrabajadores()
        } catch (e: Exception) {
            Log.e("TrabajadorFragment", "Error al cargar trabajadores: ${e.message}")
        }
    }

    private fun setupSearchView() {
        // Configurar el listener de búsqueda
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterWorkers(newText)
                return true
            }
        })

        // Configurar apariencia del SearchView
        try {
            binding.searchView.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.search_view_border
            )
        } catch (e: Exception) {
            Log.w("TrabajadorFragment", "No se pudo cargar el drawable search_view_border: ${e.message}")
            // Usar un color de fondo por defecto
            binding.searchView.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        }

        // Configurar el EditText interno del SearchView
        setupSearchViewEditText()
    }

    private fun setupSearchViewEditText() {
        try {
            // Solución robusta: intentar diferentes formas de obtener el EditText
            val editText = getSearchViewEditText()

            editText?.apply {
                setTextColor(Color.BLACK)
                setHintTextColor(Color.GRAY)
                hint = "Buscar trabajadores o oficios..."
            }

            binding.searchView.apply {
                isIconified = false
                clearFocus()

                // Mostrar teclado automáticamente
                post {
                    val editTextPost = getSearchViewEditText()
                    editTextPost?.apply {
                        requestFocus()
                        setTextColor(Color.BLACK)
                        setHintTextColor(Color.BLACK)

                        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("TrabajadorFragment", "Error configurando SearchView: ${e.message}")
        }
    }

    /**
     * Método híbrido más robusto y eficiente
     * Combina acceso directo con fallback recursivo
     */
    private fun getSearchViewEditText(): AutoCompleteTextView? {
        // Método 1: Acceso directo (más rápido)
        val directAccess = try {
            // Usar Resources.getIdentifier para evitar errores de compilación
            val searchSrcTextId = resources.getIdentifier(
                "search_src_text",
                "id",
                "androidx.appcompat"
            )
            if (searchSrcTextId != 0) {
                binding.searchView.findViewById<AutoCompleteTextView>(searchSrcTextId)
            } else null
        } catch (e: Exception) {
            null
        }

        // Si el acceso directo funciona, usarlo
        if (directAccess != null) {
            return directAccess
        }

        // Método 2: Fallback recursivo (más confiable)
        return findEditTextRecursively(binding.searchView)
    }

    /**
     * Busca recursivamente un AutoCompleteTextView en la jerarquía de vistas
     */
    private fun findEditTextRecursively(view: View): AutoCompleteTextView? {
        if (view is AutoCompleteTextView) {
            return view
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = findEditTextRecursively(view.getChildAt(i))
                if (child != null) return child
            }
        }
        return null
    }

    private fun filterWorkers(query: String?) {
        val texto = query?.lowercase()?.trim() ?: ""
        val listaFiltrada = if (texto.isEmpty()) {
            listaCompleta
        } else {
            listaCompleta.filter { trabajador ->
                trabajador.nombreCompleto.lowercase().contains(texto) ||
                        trabajador.oficios.any { oficio ->
                            oficio.lowercase().contains(texto)
                        }
            }
        }

        Log.d("TrabajadorFragment", "Filtrado: ${listaFiltrada.size} trabajadores encontrados para '$texto'")
        adapter.submitList(listaFiltrada)
    }

    private fun setupTextToSpeech() {
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale("es", "ES"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Idioma español no soportado")
                    textToSpeech.setLanguage(Locale("es"))
                }

                // Configurar una voz femenina amigable si está disponible
                val voices = textToSpeech.voices
                val femaleVoice = voices?.find { it.name.contains("female", ignoreCase = true) || it.name.contains("mujer", ignoreCase = true) }
                if (femaleVoice != null) {
                    textToSpeech.voice = femaleVoice
                    Log.d("TTS", "Voz femenina seleccionada: ${femaleVoice.name}")
                } else {
                    Log.w("TTS", "No se encontró una voz femenina específica, usando la predeterminada")
                }
            } else {
                Log.e("TTS", "Fallo en la inicialización de TextToSpeech")
            }
        }
    }

    private fun speakWorkersList(trabajadores: List<Trabajador>) {
        if (!::textToSpeech.isInitialized) return

        val mensaje = when (trabajadores.size) {
            0 -> "No se encontraron trabajadores"
            1 -> {
                val trabajador = trabajadores.first()
                "Se encontró un trabajador: ${trabajador.nombreCompleto}, especializado en ${trabajador.oficios.joinToString(", ")}"
            }
            else -> {
                "Se encontraron ${trabajadores.size} trabajadores. Los primeros son: " +
                        trabajadores.take(3).joinToString(", ") { it.nombreCompleto }
            }
        }

        Log.d("TTS", "Reproduciendo: $mensaje")
        textToSpeech.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null, "trabajadores_list")
    }

    private fun showKeyboard() {
        try {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val editText = getSearchViewEditText()
            editText?.let {
                imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
            }
        } catch (e: Exception) {
            Log.e("TrabajadorFragment", "Error mostrando teclado: ${e.message}")
        }
    }

    private fun hideKeyboard() {
        try {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        } catch (e: Exception) {
            Log.e("TrabajadorFragment", "Error ocultando teclado: ${e.message}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Liberar recursos de TextToSpeech
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }

        _binding = null
    }

    companion object {
        private const val ARG_TRABAJADORES = "lista_trabajadores"

        /**
         * Crear instancia del fragment con lista de trabajadores filtrados (usado por IA)
         */
        fun newInstanceWithTrabajadores(trabajadores: List<Trabajador>): TrabajadorFragment {
            val fragment = TrabajadorFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_TRABAJADORES, ArrayList(trabajadores))
            }
            fragment.arguments = args
            return fragment
        }

        /**
         * Crear instancia del fragment para mostrar todos los trabajadores
         */
        fun newInstance(): TrabajadorFragment {
            return TrabajadorFragment()
        }
    }
}