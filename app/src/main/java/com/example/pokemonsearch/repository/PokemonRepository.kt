package com.example.pokemonsearch.repository

import com.example.pokemonsearch.data.remote.PokeApi
import com.example.pokemonsearch.data.remote.responses.Pokemon
import com.example.pokemonsearch.data.remote.responses.PokemonList
import com.example.pokemonsearch.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(private val api: PokeApi) {

    suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonList> {
        return try {
            Resource.Success(api.getPokemonList(limit = limit, offset = offset))
        } catch (e: Exception) {
            Resource.Error(message = "An unknown error occurred.")
        }
    }

    suspend fun getPokemon(pokemonName: String) : Resource<Pokemon> {
        return try {
            Resource.Success(api.getPokemon(pokemonName))
        } catch (e: Exception) {
            Resource.Error(message = "An unknown error occurred.")
        }
    }
}