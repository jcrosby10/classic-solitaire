package com.huntergaming.classicsolitaire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.huntergaming.classicsolitaire.ui.compose.screens.GameScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.MainMenu
import com.huntergaming.classicsolitaire.ui.compose.screens.SplashScreen
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import dagger.hilt.android.AndroidEntryPoint

internal const val SPLASH_SCREEN_NAV_ROUTE = "classicSolitaire/SplashScreen"
internal const val MAIN_MENU_NAV_ROUTE = "classicSolitaire/MainMenu"
internal const val GAME_SCREEN_NAV_ROUTE = "classicSolitaire/GameScreen"

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClassicSolitaireTheme {
                ClassicSolitaireNavigation(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun ClassicSolitaireNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN_NAV_ROUTE
    ) {
        composable(SPLASH_SCREEN_NAV_ROUTE) { SplashScreen() }
        composable(MAIN_MENU_NAV_ROUTE) { MainMenu() }
        composable(GAME_SCREEN_NAV_ROUTE) { GameScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ClassicSolitaireTheme {
        ClassicSolitaireNavigation(navController = rememberNavController())
    }
}