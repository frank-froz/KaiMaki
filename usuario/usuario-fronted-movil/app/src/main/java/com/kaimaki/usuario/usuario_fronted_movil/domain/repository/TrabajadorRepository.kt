package com.kaimaki.usuario.usuario_fronted_movil.domain.repository

import com.kaimaki.usuario.usuario_fronted_movil.domain.model.Trabajador
import retrofit2.Call

interface TrabajadorRepository {
    suspend fun getTrabajadoresDisponibles(): List<Trabajador>
}
