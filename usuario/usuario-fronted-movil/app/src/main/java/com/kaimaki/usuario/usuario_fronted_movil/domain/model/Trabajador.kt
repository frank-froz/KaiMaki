package com.kaimaki.usuario.usuario_fronted_movil.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trabajador(
    val id: Long,
    val nombreCompleto: String,
    val oficios: List<String>,
    val direccion: String,
    val distrito: String,
    val provincia: String,
    val departamento: String,
    val fotoPerfil:String
) : Parcelable
