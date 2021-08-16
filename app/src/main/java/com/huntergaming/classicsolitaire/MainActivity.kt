package com.huntergaming.classicsolitaire

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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

        hideSystemUI()

        setContent {
            ClassicSolitaireTheme {
                ClassicSolitaireNavigation(navController = rememberNavController())
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) hideSystemUI()
    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }
}

@Composable
fun ClassicSolitaireNavigation(navController: NavHostController) {
    val loadingContent: (suspend () -> Unit)? by rememberSaveable { mutableStateOf(null) }

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN_NAV_ROUTE
    ) {
        composable(SPLASH_SCREEN_NAV_ROUTE) { SplashScreen(loadingContent, navController) }
        composable(MAIN_MENU_NAV_ROUTE) { MainMenu() }
        composable(GAME_SCREEN_NAV_ROUTE) { GameScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ClassicSolitaireTheme {
        SplashScreen(navController = rememberNavController())
    }
}