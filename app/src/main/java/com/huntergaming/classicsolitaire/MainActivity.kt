package com.huntergaming.classicsolitaire

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huntergaming.authentication.viewmodel.AuthenticationViewModel
import com.huntergaming.authentication.compose.Authentication
import com.huntergaming.authentication.compose.NAV_TO_MAIN_MENU
import com.huntergaming.authentication.compose.POP_STACK
import com.huntergaming.classicsolitaire.ui.compose.screens.GameScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.MainMenu
import com.huntergaming.classicsolitaire.ui.compose.screens.SettingsScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.SplashScreen
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.classicsolitaire.viewmodel.PlayerViewModel
import com.huntergaming.ui.uitl.CommunicationAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalPagerApi
@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    // properties

    @Inject
    lateinit var communicationAdapter: CommunicationAdapter

    private val authViewModel: AuthenticationViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    // overridden functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()

        setContent {

            val navController = rememberNavController()
            ClassicSolitaireNavigation(
                navController = navController,
                authViewModel = authViewModel,
                owner = this,
                communicationAdapter = communicationAdapter,
                context = applicationContext,
                playerViewModel = playerViewModel
            )

            if (authViewModel.isLoggedIn() == true) {
                navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route)
            }

            LaunchedEffect(true) {
                communicationAdapter.message.observe(this@MainActivity) {
                    when (it.data[0]) {
                        NAV_TO_MAIN_MENU -> {
                            navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route) {

                                if (it.data.size > 1 && it.data[1] == POP_STACK) {
                                    popUpTo(ComposableRoutes.MAIN_MENU_NAV.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        parent?.keepScreenOn = true
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    // private functions

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN
        )

        actionBar?.hide()
    }
}

// navigation

@ExperimentalPagerApi
@Composable
private fun ClassicSolitaireNavigation(
    navController: NavHostController,
    authViewModel: AuthenticationViewModel,
    playerViewModel: PlayerViewModel,
    owner: LifecycleOwner,
    context: Context,
    communicationAdapter: CommunicationAdapter,
) {
    val loadingContent: (suspend () -> Unit)? by rememberSaveable { mutableStateOf(null) }

    NavHost(
        navController = navController,
        startDestination = ComposableRoutes.SPLASH_SCREEN_NAV.route,
    ) {
        composable(ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) {
            ClassicSolitaireTheme {
                Authentication(
                    authViewModel = authViewModel,
                    owner = owner,
                    context = context,
                    communicationAdapter = communicationAdapter
                )
            }
        }

        composable(ComposableRoutes.SPLASH_SCREEN_NAV.route) {
            ClassicSolitaireTheme {
                SplashScreen(
                    navController = navController,
                    loadContent = loadingContent,
                    authViewModel = authViewModel
                )
            }
        }

        composable(ComposableRoutes.MAIN_MENU_NAV.route) {
            ClassicSolitaireTheme {
                MainMenu(
                    navController = navController
                )
            }
        }

        composable(ComposableRoutes.SETTINGS_MENU_NAV.route) {
            ClassicSolitaireTheme {
                SettingsScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    lifecycleOwner = owner,
                    playerViewModel = playerViewModel
                )
            }
        }

        composable(ComposableRoutes.GAME_SCREEN_NAV.route) { ClassicSolitaireTheme { GameScreen() } }
    }
}

internal enum class ComposableRoutes(val route: String) {
    AUTHENTICATION_SCREEN_NAV("classicSolitaire/Authentication"),
    SPLASH_SCREEN_NAV("classicSolitaire/SplashScreen"),
    MAIN_MENU_NAV("classicSolitaire/MainMenu"),
    SETTINGS_MENU_NAV("classicSolitaire/SettingsScreen"),
    GAME_SCREEN_NAV("classicSolitaire/GameScreen")
}