package com.example.pokemonsearch.presenter.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokemonsearch.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.LightGray)
        ) {
            PicForTopDrawer()
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Pokemon Info",
                    style = TextStyle(
                        fontSize = 35.sp,
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 5.sp
                    ),
                    modifier = Modifier.padding(5.dp)
                )
                screens.forEach { drawer ->
                    Content(drawer = drawer, onDestinationClicked = {
                        navController.navigate(drawer.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route = route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun Content(drawer: DrawerScreens, onDestinationClicked: (DrawerScreens) -> Unit) {
    Column() {
        Card(
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { onDestinationClicked(drawer) },
        ) {
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column() {
                    Image(
                        painter = painterResource(id = R.drawable.pokeball),
                        contentDescription = "something here",
                        modifier = Modifier
                            .size(50.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = drawer.title,
                        fontFamily = FontFamily.SansSerif,
                    )
                }
            }
        }
    }
}

@Composable
fun PicForTopDrawer() {
    Image(
        painter = painterResource(id = R.drawable.eevee),
        contentDescription = "Some Image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(width = 420.dp, height = 200.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ShowDrawer() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
}

@Preview(showBackground = true)
@Composable
fun LetsTryContent() {
    Content(drawer = DrawerScreens.Pokemon, onDestinationClicked = {})
}