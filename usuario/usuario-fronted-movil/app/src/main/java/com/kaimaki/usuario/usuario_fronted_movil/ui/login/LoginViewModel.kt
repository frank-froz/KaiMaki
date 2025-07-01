package com.kaimaki.usuario.usuario_fronted_movil.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.kaimaki.usuario.usuario_fronted_movil.data.repository.UserRepositoryImpl
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authResult = MutableLiveData<Result<AuthResponse>>()
    val authResult: LiveData<Result<AuthResponse>> = _authResult

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(correo, contrasena)
                _authResult.value = Result.success(response)
            } catch (e: Exception) {
                _authResult.value = Result.failure(e)
            }
        }
    }

    fun loginConGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val response = userRepository.loginConGoogle(idToken)
                _authResult.value = Result.success(response)
            } catch (e: Exception) {
                _authResult.value = Result.failure(e)
            }
        }
    }
}
