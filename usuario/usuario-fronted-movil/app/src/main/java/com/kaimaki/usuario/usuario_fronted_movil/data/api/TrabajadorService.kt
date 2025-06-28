package com.kaimaki.usuario.usuario_fronted_movil.data.api


import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface TrabajadorService {

    @GET("/api/trabajadores/disponibles")
    suspend fun getTrabajadoresDisponibles(
        @Header("Authorization") token: String
    ): List<Trabajador>

    @GET("/api/trabajadores/{id}")
    fun obtenerTrabajadorPorId(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    ): Call<Trabajador>

    @GET("/api/trabajadores/buscar")
    suspend fun getTrabajadoresPorOficio(
        @Header("Authorization") token: String,
        @Query("oficio") oficio: String
    ): Response<List<Trabajador>>


}
