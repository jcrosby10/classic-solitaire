package com.huntergaming.classicsolitaire

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.huntergaming.authentication.viewmodel.AuthenticationViewModel
import com.huntergaming.ui.composable.Authentication
import com.huntergaming.classicsolitaire.ui.compose.screens.GameScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.MainMenu
import com.huntergaming.classicsolitaire.ui.compose.screens.SettingsScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.SplashScreen
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.gamedata.viewmodel.PlayerViewModel
import com.huntergaming.ui.composable.HunterGamingAlertDialog
import com.huntergaming.ui.uitl.HunterGamingObserver
import com.huntergaming.ui.uitl.CommunicationAdapter
import com.huntergaming.ui.uitl.navigationRoute
import com.huntergaming.web.InternetStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    // properties

    @Inject
    lateinit var communicationAdapter: CommunicationAdapter

    @Inject
    lateinit var internetStatus: InternetStatus

    private val authViewModel: AuthenticationViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    // overridden functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideSystemUI()

        setContent {

            val navController = rememberNavController()

            ClassicSolitaireTheme {
                ClassicSolitaireNavigation(
                    navController = navController,
                    authViewModel = authViewModel,
                    owner = this,
                    communicationAdapter = communicationAdapter,
                    context = applicationContext,
                    playerViewModel = playerViewModel
                )
            }

            if (authViewModel.isLoggedIn() == true) {
                navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route)
            }

            rememberCoroutineScope().launch {
                navigationRoute
                    .filterNot { it == "" }
                    .collect {
                        navController.navigate(it)
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        internetStatus.register()
    }

    override fun onStop() {
        super.onStop()
        internetStatus.unregister()
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
    communicationAdapter: CommunicationAdapter
) {
    val loadingContent: (suspend () -> Unit)? by rememberSaveable { mutableStateOf(null) }

    val showProgressIndicator = remember { mutableStateOf(false) }

    val dialogState = remember { mutableStateOf(false) }
    val dialogText = remember { mutableStateOf("") }
    val dialogTitle = remember { mutableStateOf(R.string.default_string) }
    val onConfirm = remember { mutableStateOf({}) }
    val onDismiss = remember { mutableStateOf({}) }
    val dialogBackgroundImage = R.drawable.dialog_bg
    val dismissOnBackPress = remember { mutableStateOf(true) }
    val dismissOnClickOutside = remember { mutableStateOf(true) }

    HunterGamingObserver(
        dialogState = dialogState,
        owner = owner,
        communicationAdapter = communicationAdapter,
        dialogTitle = dialogTitle,
        dialogMessage = dialogText,
        context = context,
        navController = navController,
        mainMenuRoute = ComposableRoutes.MAIN_MENU_NAV.route,
        showProgressIndicator = showProgressIndicator
    )
        .apply {
            startErrorObserver(owner = owner)
            startCreateAccountStateObserver()
            startLoggedInStateObserver()
        }

    Box {
        NavHost(
            navController = navController,
            startDestination = ComposableRoutes.SPLASH_SCREEN_NAV.route,
        ) {

            composable(ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) {
                ClassicSolitaireTheme {
                    Authentication(
                        background = R.drawable.menu_bg
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
                        playerViewModel = playerViewModel,
                        dialogState = dialogState,
                        dialogTitle = dialogTitle,
                        dialogText = dialogText,
                        onConfirm = onConfirm
                    )
                }
            }

            composable(ComposableRoutes.GAME_SCREEN_NAV.route) { ClassicSolitaireTheme { GameScreen() } }
        }

        if (showProgressIndicator.value) {
            CircularProgressIndicator()
        }

        HunterGamingAlertDialog(
            onConfirm = onConfirm.value,
            title = dialogTitle.value,
            text = dialogText.value,
            backgroundImage = dialogBackgroundImage,
            state = dialogState,
            onDismiss = onDismiss.value,
            dismissOnBackPress = dismissOnBackPress.value,
            dismissOnClickOutside = dismissOnClickOutside.value
        )
    }
}

enum class ComposableRoutes(val route: String) {
    AUTHENTICATION_SCREEN_NAV("classicSolitaire/Authentication"),
    SPLASH_SCREEN_NAV("classicSolitaire/SplashScreen"),
    MAIN_MENU_NAV("classicSolitaire/MainMenu"),
    SETTINGS_MENU_NAV("classicSolitaire/SettingsScreen"),
    GAME_SCREEN_NAV("classicSolitaire/GameScreen")
}