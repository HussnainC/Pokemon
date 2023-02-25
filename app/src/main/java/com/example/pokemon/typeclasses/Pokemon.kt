package com.example.pokemon.typeclasses


import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("pokemon")
    val pokemon: PokemonX,
    @SerializedName("slot")
    val slot: Int
)