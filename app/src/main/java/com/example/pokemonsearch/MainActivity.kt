package com.example.pokemonsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemonsearch.presenter.HomeScreen
import com.example.pokemonsearch.presenter.SingleScreenApp
import com.example.pokemonsearch.presenter.drawer.DrawerScreens
import com.example.pokemonsearch.presenter.listscreen.PokemonListScreen
import com.example.pokemonsearch.ui.theme.PokemonSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonSearchTheme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "landing_page"
                    ) {

                        composable(route = "landing_page") {
                            SingleScreenApp()
                        }

                        composable(route = DrawerScreens.Home.route) {
                            HomeScreen()
                        }
                        composable(route = DrawerScreens.Pokemon.route) {
                            PokemonListScreen(navController = navController)
                        }
                        composable(route = DrawerScreens.Moves.route) {
                            // SOMETHING
                        }
                    }
                }
            }
        }
    }
}