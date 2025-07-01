package com.kaimaki.usuario.usuario_fronted_movil.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.UserRepositoryImpl

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val userRepository = UserRepositoryImpl(context)
        return LoginViewModel(userRepository) as T
    }
}
