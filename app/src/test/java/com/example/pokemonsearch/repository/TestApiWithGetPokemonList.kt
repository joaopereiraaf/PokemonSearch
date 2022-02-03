package com.example.pokemonsearch.repository

import com.example.pokemonsearch.coroutines_util.CoroutineTestRule
import com.example.pokemonsearch.data.remote.PokeApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.mockk.clearAllMocks
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.MockResponseFileReader

@RunWith(JUnit4::class)
class TestApiWithGetPokemonList {

    //executes each task synchronously
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val server = MockWebServer()
    private lateinit var repository: PokemonRepository
    private val gson = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/").toString()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
        repository = PokemonRepository(service)
    }

    @Test
    fun `test api success`() {
        val mockedResponse = MockResponseFileReader("testingApi/testGetPokemonList.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { repository.getPokemonList(anyInt(), anyInt()) }
        val json = gson.toJson(response.data)

        val resultResponse = JsonParser.parseString(json)
        val expectedResponse = JsonParser.parseString(mockedResponse)
        assertNotNull(resultResponse)
        assertEquals(resultResponse, expectedResponse)
    }

    @Test
    fun `test api failure`() {
        val response = runBlocking { repository.getPokemonList(anyInt(), anyInt()) }
        assertNull(response.data)
        assertEquals("An unknown error occurred.", response.message)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}