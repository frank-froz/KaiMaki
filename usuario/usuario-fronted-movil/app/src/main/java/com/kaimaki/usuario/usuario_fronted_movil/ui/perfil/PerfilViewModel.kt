package com.kaimaki.usuario.usuario_fronted_movil.ui.perfil

import androidx.lifecycle.*
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Usuario
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.UserRepository
import kotlinx.coroutines.launch
class PerfilViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    fun cargarPerfil() {
        viewModelScope.launch {
            try {
                val perfil = userRepository.getPerfil()
                _usuario.value = perfil
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }

    fun actualizarPerfil(usuario: Usuario) {
        viewModelScope.launch {
            try {
                val exito = userRepository.actualizarPerfil(usuario)
                if (exito) {
                    _mensaje.value = "Perfil actualizado correctamente"
                    _usuario.value = usuario
                } else {
                    _mensaje.value = "Error al actualizar"
                }
            } catch (e: Exception) {
                _mensaje.value = "Excepción: ${e.message}"
            }
        }
    }
}
