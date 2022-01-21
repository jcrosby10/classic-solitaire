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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huntergaming.authentication.viewmodel.AuthenticationViewModel
import com.huntergaming.authentication.ui.Authentication
import com.huntergaming.authentication.ui.NAV_TO_MAIN_MENU
import com.huntergaming.classicsolitaire.adapter.PreferencesAdapter
import com.huntergaming.classicsolitaire.ui.compose.screens.GameScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.MainMenu
import com.huntergaming.classicsolitaire.ui.compose.screens.SettingsScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.SplashScreen
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.ui.composable.HunterGamingAlertDialog
import com.huntergaming.ui.uitl.CommunicationAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalPagerApi
@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesAdapter: PreferencesAdapter

    @Inject
    lateinit var communicationAdapter: CommunicationAdapter

    private val authViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()

        setContent {

            val showDialog = remember { mutableStateOf(preferencesAdapter.shownDataConsent()) }

            HunterGamingAlertDialog(
                state = showDialog,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                onConfirm = {
                    showDialog.value = false
                    preferencesAdapter.setCanUseFirebase(true)
                    preferencesAdapter.updateDataConsent()
                },
                onDismiss = {
                    showDialog.value = false
                    preferencesAdapter.setCanUseFirebase(false)
                    preferencesAdapter.updateDataConsent()
                },
                title = R.string.data_consent,
                text = R.string.data_consent_description,
                backgroundImage = R.drawable.dialog_bg
            )

            val navController = rememberNavController()
            ClassicSolitaireNavigation(
                navController = navController,
                authViewModel = authViewModel,
                owner = this,
                communicationAdapter = communicationAdapter,
                context = applicationContext
            )

            LaunchedEffect(true) {
                communicationAdapter.message.observe(this@MainActivity) {
                    when (it.data) {
                        NAV_TO_MAIN_MENU -> navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route)
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
private fun ClassicSolitaireNavigation(
    navController: NavHostController,
    authViewModel: AuthenticationViewModel,
    owner: LifecycleOwner,
    context: Context,
    communicationAdapter: CommunicationAdapter
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
                    navController = navController
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