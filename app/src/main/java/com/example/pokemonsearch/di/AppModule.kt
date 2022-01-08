package com.example.pokemonsearch.di

import com.example.pokemonsearch.data.remote.PokeApi
import com.example.pokemonsearch.repository.PokemonRepository
import com.example.pokemonsearch.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api = api)

//    @Provides
//    @Singleton
    fun providePokeApi() : PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}