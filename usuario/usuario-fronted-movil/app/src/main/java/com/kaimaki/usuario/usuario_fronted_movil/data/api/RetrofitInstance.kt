package com.kaimaki.usuario.usuario_fronted_movil.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/" //10.0.2.2

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val trabajadorApi: TrabajadorService by lazy {
        retrofit.create(TrabajadorService::class.java)
    }
    val nlpApi: NlpApi by lazy {
        retrofit.create(NlpApi::class.java)
    }
    val chatApi: ChatApi by lazy {
        retrofit.create(ChatApi::class.java)
    }





}
