package com.kaimaki.usuario.usuario_fronted_movil.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.AuthResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _authResult = MutableLiveData<Result<AuthResponse>>()
    val authResult: LiveData<Result<AuthResponse>> = _authResult

    fun login(correo: String, contrasena: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(correo, contrasena)
                val response = RetrofitInstance.authApi.login(request)
                if (response.isSuccessful && response.body() != null) {
                    _authResult.value = Result.success(response.body()!!)
                } else {
                    _authResult.value = Result.failure(Exception("Credenciales inválidas"))
                }
            } catch (e: Exception) {
                _authResult.value = Result.failure(e)
            }
        }
    }
}
