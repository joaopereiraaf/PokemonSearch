package com.example.pokemonsearch.presenter

import androidx.lifecycle.ViewModel
import com.example.pokemonsearch.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

}