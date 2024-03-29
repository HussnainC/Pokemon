package com.example.pokemon.repositories

import android.content.Context
import com.example.pokemon.abilityclasses.AbilityDataClass
import com.example.pokemon.enums.FilterType
import com.example.pokemon.resultclasses.ResultDataClass
import com.example.pokemon.interfaces.ResultCall
import com.example.pokemon.weclient.WebClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DataRepositiory @Inject constructor(val context: Context, val webClient: WebClient) {

    fun loadPokemones(result: ResultCall<Any?>) {
        webClient.api()?.getPokemons()?.enqueue(ResponseCallBack(result))
    }

    fun loadPokemoneInfo(id: String, result: ResultCall<Any?>) {
        webClient.api()?.getPokemonInfo(id)?.enqueue(ResponseCallBack(result))
    }

    fun loadFilterOptions(filterType: FilterType, resultCall: ResultCall<Any?>) {
        when (filterType) {
            FilterType.BYTYPE -> {
                webClient.api()?.getTypeOptions("type")
                    ?.enqueue(ResponseCallBack(resultCall))
            }
            FilterType.BYABILITY -> {
                webClient.api()?.getTypeOptions("ability")
                    ?.enqueue(ResponseCallBack(resultCall))
            }
        }
    }

    fun loadFilterPokemons(endPoint: String, resultCall: ResultCall<Any?>) {
        when {
            endPoint.contains("type") -> {
                webClient.api()?.getPokemonsByType(endPoint)
                    ?.enqueue(ResponseCallBack(resultCall))
            }
            endPoint.contains("ability") -> {
                webClient.api()?.getPokemonsByAbility(endPoint)
                    ?.enqueue(ResponseCallBack(resultCall))
            }

        }
    }


    private open class ResponseCallBack<T>(val resultCall: ResultCall<Any?>) : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                resultCall.resultSuccess(response.body())
            } else {
                resultCall.onFail("Fail to load")
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            resultCall.onFail(t.message.toString())
        }

    }
}