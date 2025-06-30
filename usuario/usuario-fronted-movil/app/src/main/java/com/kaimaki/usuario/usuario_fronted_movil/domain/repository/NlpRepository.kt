package com.kaimaki.usuario.usuario_fronted_movil.domain.repository

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.NlpResponse

interface NlpRepository {
    suspend fun analizarTexto(mensaje: String): Result<NlpResponse>
}
