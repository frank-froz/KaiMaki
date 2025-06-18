package com.kaimaki.usuario.usuario_fronted_movil.ui.trabajadores


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaimaki.usuario.usuario_fronted_movil.data.repository.TrabajadorRepositoryImpl
import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.util.TokenManager

class TrabajadorViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val tokenManager = TokenManager(context)
        val repository = TrabajadorRepositoryImpl(
            RetrofitInstance.trabajadorApi
        ) {
            tokenManager.getToken()
        }
        return TrabajadorViewModel(repository) as T
    }
}