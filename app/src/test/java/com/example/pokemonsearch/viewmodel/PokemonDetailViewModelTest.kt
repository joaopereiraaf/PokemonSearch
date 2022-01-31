package com.example.pokemonsearch.viewmodel

import com.example.pokemonsearch.data.remote.responses.Pokemon
import com.example.pokemonsearch.presenter.detailscreen.PokemonDetailViewModel
import com.example.pokemonsearch.repository.PokemonRepository
import com.google.gson.GsonBuilder
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import util.MockResponseFileReader

class PokemonDetailViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    private var repository: PokemonRepository = mockk()
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    private val gson = GsonBuilder().setLenient().serializeNulls().create()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
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

        coVerify { repository.getPokemonList(anyInt(), anyInt()) wasNot called }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}