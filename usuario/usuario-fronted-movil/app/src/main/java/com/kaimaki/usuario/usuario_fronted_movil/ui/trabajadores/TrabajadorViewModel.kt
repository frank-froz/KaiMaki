package com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.TrabajadorRepository
import kotlinx.coroutines.launch

class TrabajadorViewModel(
    private val repository: TrabajadorRepository
) : ViewModel() {

    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> = _trabajadores

    private var listaCompleta: List<Trabajador> = emptyList()

    fun cargarTrabajadores() {
        viewModelScope.launch {
            try {
                listaCompleta = repository.getTrabajadoresDisponibles()
                _trabajadores.value = listaCompleta
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun cargarTrabajadoresPorOficio(oficio: String) {
        viewModelScope.launch {
            try {
                val trabajadoresFiltrados = repository.getTrabajadoresPorOficio(oficio)
                _trabajadores.value = trabajadoresFiltrados
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun filtrarTrabajadores(query: String) {
        _trabajadores.value = if (query.isBlank()) {
            listaCompleta
        } else {
            listaCompleta.filter { trabajador ->
                trabajador.nombreCompleto.contains(query, ignoreCase = true) ||
                        trabajador.distrito.contains(query, ignoreCase = true) ||
                        trabajador.oficios.any { it.contains(query, ignoreCase = true) }
            }
        }
    }
}
