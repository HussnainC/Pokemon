package com.example.pokemon.interfaces

import com.example.pokemon.abilityclasses.AbilityDataClass
import com.example.pokemon.resultclasses.ResultDataClass
import com.example.pokemon.typeclasses.AbilityTypeDataClass
import com.example.pokemon.typeclasses.TypeDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("pokemon?limit=100000&offset=0")
    fun getPokemons(): Call<ResultDataClass>

    @GET("pokemon/{id}")
    fun getPokemonInfo(
        @Path("id") id: String
    ): Call<AbilityDataClass>

    @GET("{s}")
    fun getTypeOptions(@Path("s") s: String): Call<ResultDataClass>


    @GET("{s}")
    fun getPokemonsByType(@Path("s") s: String): Call<TypeDataClass>

    @GET("{s}")
    fun getPokemonsByAbility(@Path("s") s: String): Call<AbilityTypeDataClass>

}