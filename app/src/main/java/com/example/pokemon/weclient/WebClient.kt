package com.example.pokemon.weclient

import android.content.Context
import com.example.pokemon.interfaces.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class WebClient @Inject constructor(val context: Context) {
    private val BASE_URL = "https://pokeapi.co/api/v2/"
    private var myApi: API? = null

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        myApi = retrofit.create(API::class.java)
    }

    fun api(): API? {
        return myApi
    }

}