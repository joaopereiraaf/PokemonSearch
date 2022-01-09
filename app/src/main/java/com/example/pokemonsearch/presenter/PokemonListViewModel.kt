package com.example.pokemonsearch.presenter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonsearch.models.PokemonListEntry
import com.example.pokemonsearch.repository.PokemonRepository
import com.example.pokemonsearch.util.Constants.PAGE_SIZE
import com.example.pokemonsearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    init {
        loadPokemonPaginated()
    }

    private var currentPage = 0
    var pokemonList = mutableStateOf<List<PokemonListEntry>>(listOf())
    var loadError = mutableStateOf("")
//    var isLoading = mutableStateOf(false)
    var endOfListReacher = mutableStateOf(false)

    fun loadPokemonPaginated() {
        viewModelScope.launch {
//            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
            when(result) {
                is Resource.Success -> {
                    endOfListReacher.value = currentPage * PAGE_SIZE >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val urlPng = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png"
                        val urlSvg = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/${number}.svg"
                        PokemonListEntry(entry.name.capitalize(Locale.ROOT), urlPng, number.toInt())
                    }
                    currentPage++

                    loadError.value = ""
//                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
//                    isLoading.value = false
                }
            }
        }
    }

}