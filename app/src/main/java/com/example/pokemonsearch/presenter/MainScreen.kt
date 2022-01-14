package com.example.pokemonsearch.presenter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonsearch.R
import com.example.pokemonsearch.presenter.detailscreen.PokemonDetailScreen
import com.example.pokemonsearch.presenter.drawer.Drawer
import com.example.pokemonsearch.presenter.drawer.DrawerScreens
import com.example.pokemonsearch.presenter.listscreen.PokemonListScreen
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SingleScreenApp() {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(
            title = { Text(
                text = "Pokemon App",
                color = Color.White
            ) },
            backgroundColor = Color.Black,
            navigationIcon = {
                ChangeDynamicIcon(
                    navController = navController,
                    scope = scope,
                    scaffoldState = scaffoldState
                )
            },
        ) },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController) },
        content =  { NavigationOfScreens(navController = navController) }
    )
}

@Composable
fun NavigationIcon(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    IconButton(
        modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
        onClick = { scope.launch { scaffoldState.drawerState.open() } },
    ) {
        Icon(imageVector = Icons.Default.Menu,
            contentDescription = "Drawer",
            tint = Color.White,
        )
    }
}

@Composable
fun HomeScreen() {
    Image(
        painter = rememberCoilPainter(request = R.drawable.pokeball),
        contentDescription = "Home",
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            ),
        colorFilter = ColorFilter.tint(Color.Gray)
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NavigationOfScreens(navController: NavHostController) {
    NavHost(navController, startDestination = DrawerScreens.Home.route) {
        composable(DrawerScreens.Home.route) {
            HomeScreen()
        }
        composable(DrawerScreens.Pokemon.route) {
            PokemonListScreen(navController = navController)
            // PokemonListScreen w SearchBar
        }
        composable(DrawerScreens.Moves.route) {
            // SOMETHING
        }
        composable(
            route = "pokemon_list_screen") {
            PokemonListScreen(navController = navController)
        }
        composable(
            route = "pokemon_detail_screen/{pokemonName}",
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

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "An Unknown Error Occurred.",
            fontFamily = FontFamily.SansSerif,
            fontSize = 30.sp,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ChangeDynamicIcon(navController: NavController, scope: CoroutineScope, scaffoldState: ScaffoldState) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute == "pokemon_detail_screen/{pokemonName}") {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Drawer",
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .offset(x = 13.dp, y = 2.dp)
                .clickable {
                    navController.navigate("pokemon_list_screen")
                }
        )
    } else {
        NavigationIcon(scope = scope, scaffoldState = scaffoldState)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen()
}