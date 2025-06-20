package com.kaimaki.usuario.usuario_fronted_movil.util

import android.content.Context

class TokenManager(private val context: Context) {
    fun getToken(): String? {
        val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    companion object {
        fun getToken(context: Context): String? {
            val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
            return prefs.getString("token", null)
        }
    }
}