package com.kaimaki.usuario.usuario_fronted_movil.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.UserRepository

class PerfilViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PerfilViewModel(userRepository) as T
    }
}

