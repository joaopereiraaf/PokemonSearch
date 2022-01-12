package com.example.pokemonsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.text.toLowerCase
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonsearch.presenter.detailscreen.PokemonDetailScreen
import com.example.pokemonsearch.presenter.listscreen.PokemonListScreen
import com.example.pokemonsearch.ui.theme.PokemonSearchTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonSearchTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()

                    NavHost(navController = navController,
                        startDestination = "pokemon_list_screen") {
                        composable("pokemon_list_screen") {
                            PokemonListScreen(navController = navController)
                        }
                        composable("pokemon_detail_screen/{pokemonName}",
                            arguments = listOf(
                                navArgument("pokemonName") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val pokemonName = remember {
                                it.arguments?.getString("pokemonName")
                            }
                            PokemonDetailScreen(
                                pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}