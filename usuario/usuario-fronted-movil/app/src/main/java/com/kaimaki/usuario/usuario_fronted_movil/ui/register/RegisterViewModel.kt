package com.kaimaki.usuario.usuario_fronted_movil.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.UserRepositoryImpl
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.UserRepository
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() {

    private val repository: UserRepository = UserRepositoryImpl()

    private val _registroResult = MutableLiveData<Result<AuthResponse>>()
    val registroResult: LiveData<Result<AuthResponse>> = _registroResult

    fun registrarUsuario(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(correo, contrasena)
                _registroResult.value = Result.success(response)
            } catch (e: Exception) {
                _registroResult.value = Result.failure(e)
            }
        }
    }
}
