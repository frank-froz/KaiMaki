package com.kaimaki.usuario.usuario_fronted_movil.data.repository

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
import com.kaimaki.usuario.usuario_fronted_movil.domain.repository.TrabajadorRepository
import com.kaimaki.usuario.usuario_fronted_movil.data.api.TrabajadorService

class TrabajadorRepositoryImpl(
    private val service: TrabajadorService,
    private val tokenProvider: () -> String?
) : TrabajadorRepository {

    override suspend fun getTrabajadoresDisponibles(): List<Trabajador> {
        val token = tokenProvider()
        if (token.isNullOrEmpty()) {
            throw Exception("Token de autorización no disponible")
        }
        return service.getTrabajadoresDisponibles("Bearer $token")
    }
}
