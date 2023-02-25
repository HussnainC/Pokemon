package com.example.pokemon.modules

import android.content.Context
import com.example.pokemon.db.DataBaseUtil
import com.example.pokemon.repositories.DataRepositiory
import com.example.pokemon.weclient.WebClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ViewmodelModulesComponents {
    @Provides
    @ViewModelScoped
    fun webClient(@ApplicationContext context: Context) = WebClient(context)

    @Provides
    @ViewModelScoped
    fun dataRepo(@ApplicationContext context: Context) =
        DataRepositiory(context, webClient(context))

    @Provides
    @ViewModelScoped
    fun dataBaseUtil(@ApplicationContext context: Context) = DataBaseUtil(context)
}