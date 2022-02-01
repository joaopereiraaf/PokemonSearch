package com.example.pokemonsearch.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.example.pokemonsearch.CoroutineTestRule
import com.example.pokemonsearch.data.remote.responses.PokemonList
import com.example.pokemonsearch.presenter.listscreen.PokemonListViewModel
import com.example.pokemonsearch.repository.PokemonRepository
import com.example.pokemonsearch.util.Resource
import com.google.gson.GsonBuilder
import io.mockk.*
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import util.MockResponseFileReader

class PokemonListViewModelTest {

    //executes each task synchronously
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val repository: PokemonRepository = mockk()
    private lateinit var pokemonViewModel: PokemonListViewModel
    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    @Before
    fun init() {
        pokemonViewModel = PokemonListViewModel(repository = repository)
    }

    @Test
    fun `verify that getPokemonList() is called on searchPokemonList()`() {
        // GIVEN
        val data = MockResponseFileReader("testingApi/testGetPokemonList.json").content
        val mockedPokemonList = gson.fromJson(data, PokemonList::class.java)
        coEvery { repository.getPokemonList(anyInt(), anyInt()) } returns Resource.Success(mockedPokemonList)
        pokemonViewModel.isSearching = mutableStateOf(false)

        // WHEN
        runBlocking { pokemonViewModel.searchPokemonList("1") }

        // THEN
        assertEquals(false, pokemonViewModel.isSearching.value)
        assertNotNull(pokemonViewModel.cachedPokemonList)
        coVerify { repository.getPokemonList(20, 0) }

        coVerify { repository.getPokemon(any()) wasNot called }
    }

    @Test
    fun `test that getPokemonList() with ResourceSuccess, is called on loadPokemonPaginated()`() {
        // GIVEN
        val data = MockResponseFileReader("testingApi/testGetPokemonList.json").content
        val mockedPokemonList = gson.fromJson(data, PokemonList::class.java)
        coEvery { repository.getPokemonList(anyInt(), anyInt()) } returns Resource.Success(mockedPokemonList)
        pokemonViewModel.isSearching = mutableStateOf(false)

        // WHEN
        runBlocking { pokemonViewModel.loadPokemonPaginated() }

        // THEN
        assertEquals(false, pokemonViewModel.isSearching.value)
        assertNotNull(pokemonViewModel.cachedPokemonList)

        coVerify { repository.getPokemon(any()) wasNot called }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}