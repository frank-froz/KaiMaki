package com.kaimaki.usuario.usuario_fronted_movil.data.api

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.NlpRequest
import com.kaimaki.usuario.usuario_fronted_movil.domain.model.NlpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NlpApi {
    @POST("api/nlp/analizar")
    suspend fun analizarTexto(@Body request: NlpRequest): Response<NlpResponse>
}
