package com.kaimaki.usuario.usuario_fronted_movil.ui.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.UserRepositoryImpl

class RegisterViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = UserRepositoryImpl(context)
        return RegisterViewModel(repo) as T
    }
}
