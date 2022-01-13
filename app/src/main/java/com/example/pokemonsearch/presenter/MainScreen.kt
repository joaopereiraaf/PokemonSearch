package com.example.pokemonsearch.presenter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemonsearch.R
import com.example.pokemonsearch.presenter.drawer.Drawer
import com.example.pokemonsearch.presenter.drawer.DrawerScreens
import com.example.pokemonsearch.presenter.listscreen.PokemonListScreen
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val lightLightGray = Color(0xFFBDBDBD)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SingleScreenApp(pokemonName: MutableList<String>) {

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
            backgroundColor = Color.Gray,
            navigationIcon = {
                NavigationIcon(scope = scope, scaffoldState = scaffoldState) },
        ) },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController) },
        content =  { Navigation(navController = navController, pokemonName) }
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
            .background(lightLightGray),
        colorFilter = ColorFilter.tint(Color.Gray)
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController, pokemonName: MutableList<String>) {
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
    }
}

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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