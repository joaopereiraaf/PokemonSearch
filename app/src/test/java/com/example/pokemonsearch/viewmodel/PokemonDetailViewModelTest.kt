package com.example.pokemonsearch.viewmodel

import com.example.pokemonsearch.data.remote.PokeApi
import com.example.pokemonsearch.data.remote.responses.Pokemon
import com.example.pokemonsearch.data.remote.responses.PokemonList
import com.example.pokemonsearch.presenter.detailscreen.PokemonDetailViewModel
import com.example.pokemonsearch.presenter.listscreen.PokemonListViewModel
import com.example.pokemonsearch.repository.PokemonRepository
import com.google.gson.GsonBuilder
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.any
import util.MockResponseFileReader
import kotlin.math.exp

class PokemonDetailViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()
    private lateinit var api: PokeApi
    private lateinit var repository: PokemonRepository
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        api = mockk()
        repository = PokemonRepository(api)
        pokemonDetailViewModel = PokemonDetailViewModel(repository = repository)
    }

    @Test
    fun `test if getPokemonInfo() returns Pokemon`() {
        //GIVEN
        val data = MockResponseFileReader("testingApi/pokemonDetails.json").content
        val mockedPokemon = gson.fromJson(data, Pokemon::class.java)
        coEvery { pokemonDetailViewModel.getPokemonInfo(any()).data } returns mockedPokemon

        //WHEN
        val expected = runBlocking { pokemonDetailViewModel.getPokemonInfo("charmander").data }

        //THEN
        assertEquals(expected, mockedPokemon)
        assertNotNull(expected)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}