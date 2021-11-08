package com.huntergaming.classicsolitaire

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huntergaming.classicsolitaire.ui.compose.screens.GameScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.MainMenu
import com.huntergaming.classicsolitaire.ui.compose.screens.SettingsScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.SplashScreen
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.composables.HunterGamingAlertDialog
import com.huntergaming.composables.HunterGamingBodyText
import com.huntergaming.composables.HunterGamingButton
import com.huntergaming.composables.HunterGamingTitleText
import com.huntergaming.gamedata.preferences.HunterGamingPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalPagerApi
@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var playerSettings: HunterGamingPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()

        setContent {
            val showDialog = remember { mutableStateOf(playerSettings.shownDataConsent()) }

            if (showDialog.value) {
                HunterGamingAlertDialog(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    confirmButton = {
                        HunterGamingButton(
                            onClick = {
                                showDialog.value = false
                                playerSettings.setCanUseFirebase(true)
                            },
                            text = R.string.button_yes
                        )
                    },
                    dismissButton = {
                        HunterGamingButton(
                            onClick = {
                                showDialog.value = false
                                playerSettings.setCanUseFirebase(false)
                            },
                            text = R.string.button_no
                        )
                    },
                    title = {
                        HunterGamingTitleText(
                            text = R.string.data_consent
                        )
                    },
                    text = {
                        HunterGamingBodyText(
                            text = R.string.data_consent_description
                        )
                    }
                )

                playerSettings.updateDataConsent()
            }

            val navController = rememberNavController()
            ClassicSolitaireNavigation(
                navController = navController
            )
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

@ExperimentalPagerApi
@Composable
private fun ClassicSolitaireNavigation(navController: NavHostController) {
    val loadingContent: (suspend () -> Unit)? by rememberSaveable { mutableStateOf(null) }

    NavHost(
        navController = navController,
        startDestination = ComposableRoutes.SPLASH_SCREEN_NAV.route
    ) {
        composable(ComposableRoutes.SPLASH_SCREEN_NAV.route) {
            ClassicSolitaireTheme {
                SplashScreen(
                    navController = navController,
                    loadContent = loadingContent
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
                    navController = navController
                )
            }
        }
        composable(ComposableRoutes.GAME_SCREEN_NAV.route) { ClassicSolitaireTheme { GameScreen() } }
    }
}

internal enum class ComposableRoutes(val route: String) {
    SPLASH_SCREEN_NAV("classicSolitaire/SplashScreen"),
    MAIN_MENU_NAV("classicSolitaire/MainMenu"),
    SETTINGS_MENU_NAV("classicSolitaire/SettingsScreen"),
    GAME_SCREEN_NAV("classicSolitaire/GameScreen")
}