package com.example.pokemonsearch.presenter.drawer

sealed class DrawerScreens(val title: String, val route: String) {
    object Home : DrawerScreens("Home", "home")
    object Pokemon : DrawerScreens("Pokemon", "pokemon")
    object Moves : DrawerScreens( "Moves", "moves")
}

val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Pokemon,
    DrawerScreens.Moves
)