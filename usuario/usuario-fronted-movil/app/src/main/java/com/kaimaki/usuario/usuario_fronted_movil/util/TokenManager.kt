package com.kaimaki.usuario.usuario_fronted_movil.util

import android.content.Context

class TokenManager(private val context: Context) {

    fun setToken(token: String) {
        val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    fun setUserId(userId: Long) {
        val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        prefs.edit().putLong("user_id", userId).apply()
    }

    fun getUserId(): Long {
        val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
        return prefs.getLong("user_id", -1L)
    }

    companion object {
        fun setToken(context: Context, token: String) {
            val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
            prefs.edit().putString("token", token).apply()
        }

        fun getToken(context: Context): String? {
            val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
            return prefs.getString("token", null)
        }

        fun setUserId(context: Context, userId: Long) {
            val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
            prefs.edit().putLong("user_id", userId).apply()
        }

        fun getUserId(context: Context): Long {
            val prefs = context.getSharedPreferences("usuario_prefs", Context.MODE_PRIVATE)
            return prefs.getLong("user_id", -1L)
        }
        fun getAuthHeader(context: Context): String? {
            return getToken(context)?.let { "Bearer $it" }
        }

    }
}
