package com.huntergaming.classicsolitaire

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.auth.api.Auth
import com.huntergaming.authentication.adapter.AuthenticationAdapter
import com.huntergaming.authentication.ui.Authentication
import com.huntergaming.classicsolitaire.adapter.PreferencesAdapter
import com.huntergaming.classicsolitaire.ui.compose.screens.GameScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.MainMenu
import com.huntergaming.classicsolitaire.ui.compose.screens.SettingsScreen
import com.huntergaming.classicsolitaire.ui.compose.screens.SplashScreen
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.ui.composable.HunterGamingAlertDialog
import com.huntergaming.ui.composable.HunterGamingBodyText
import com.huntergaming.ui.composable.HunterGamingButton
import com.huntergaming.ui.composable.HunterGamingTitleText
import com.huntergaming.ui.uitl.CommunicationAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    companion object {
        private const val PLAY_GAMES_SIGN_IN = "googlePlaySignIn"
        private const val LOG_TAG = "MainActivity"
    }

    @Inject
    lateinit var preferencesAdapter: PreferencesAdapter

    @Inject
    lateinit var communicationAdapter: CommunicationAdapter

    @Inject
    internal lateinit var authAdapter: AuthenticationAdapter

    private val loginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data!!.hasExtra(PLAY_GAMES_SIGN_IN)) {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(result.data!!)
                    authAdapter.onManualResult(googleSignInResult!!, this@MainActivity)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authAdapter.loginManually.observe(this) {
            val signInClient = authAdapter.signInClient
            val intent = signInClient.signInIntent
            intent.putExtra(PLAY_GAMES_SIGN_IN, PLAY_GAMES_SIGN_IN)
            loginResult.launch(intent)
        }

        hideSystemUI()

        setContent {
            val failures = communicationAdapter.error.observeAsState()
            val closeDialog = remember { mutableStateOf(false) }

            if (!failures.value.isNullOrEmpty() && !closeDialog.value) {
                Log.e(LOG_TAG, failures.value!!)

                HunterGamingAlertDialog(
                    confirmButton = {
                        HunterGamingButton(
                            onClick = { closeDialog.value = true },
                            text = com.huntergaming.authentication.R.string.button_ok
                        )
                    },
                    title = { HunterGamingTitleText(text = com.huntergaming.authentication.R.string.error_title) },
                    text = { HunterGamingBodyText(text = failures.value!!) },
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                )
            }

            val showDialog = remember { mutableStateOf(preferencesAdapter.shownDataConsent()) }

            if (showDialog.value) {
                HunterGamingAlertDialog(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    confirmButton = {
                        HunterGamingButton(
                            onClick = {
                                showDialog.value = false
                                preferencesAdapter.setCanUseFirebase(true)
                            },
                            text = R.string.button_yes
                        )
                    },
                    dismissButton = {
                        HunterGamingButton(
                            onClick = {
                                showDialog.value = false
                                preferencesAdapter.setCanUseFirebase(false)
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

                preferencesAdapter.updateDataConsent()
            }

            val navController = rememberNavController()
            ClassicSolitaireNavigation(
                navController = navController,
                communicationAdapter = communicationAdapter
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
private fun ClassicSolitaireNavigation(navController: NavHostController, communicationAdapter: CommunicationAdapter) {
    val loadingContent: (suspend () -> Unit)? by rememberSaveable { mutableStateOf(null) }

    NavHost(
        navController = navController,
        startDestination = ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route
    ) {
        composable(ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route) {
            ClassicSolitaireTheme {
                Authentication(communicationAdapter = communicationAdapter)
            }
        }

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
    AUTHENTICATION_SCREEN_NAV("classicSolitaire/Authentication"),
    SPLASH_SCREEN_NAV("classicSolitaire/SplashScreen"),
    MAIN_MENU_NAV("classicSolitaire/MainMenu"),
    SETTINGS_MENU_NAV("classicSolitaire/SettingsScreen"),
    GAME_SCREEN_NAV("classicSolitaire/GameScreen")
}