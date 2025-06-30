package com.kaimaki.usuario.usuario_fronted_movil.data.repository

import com.kaimaki.usuario.usuario_fronted_movil.data.api.RetrofitInstance
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.NlpRequest
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.NlpResponse
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.NlpRepository

class NlpRepositoryImpl : NlpRepository {
    override suspend fun analizarTexto(mensaje: String): Result<NlpResponse> {
        return try {
            val response = RetrofitInstance.nlpApi.analizarTexto(NlpRequest(mensaje))
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Respuesta vacía"))
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}