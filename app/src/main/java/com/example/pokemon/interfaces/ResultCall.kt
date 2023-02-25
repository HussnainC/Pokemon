package com.example.pokemon.interfaces

abstract class ResultCall<T> {

    open fun onFail(message: String) {}

    open fun  resultSuccess(result: T){}
}