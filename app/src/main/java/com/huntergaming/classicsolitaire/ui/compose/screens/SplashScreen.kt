package com.huntergaming.classicsolitaire.ui.compose.screens

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.huntergaming.authentication.viewmodel.AuthenticationViewModel
import com.huntergaming.classicsolitaire.BuildConfig
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.compose.ClassicSolitaireTitle
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val time: Long = if (BuildConfig.DEBUG) 1000 else 5000

// composables

@Composable
internal fun SplashScreen(
    navController: NavHostController,
    loadContent: (suspend () -> Unit)? = null,
    authViewModel: AuthenticationViewModel
) {

    if (loadContent == null) {
        val timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (authViewModel.isLoggedIn() == true) navController.navigate(ComposableRoutes.MAIN_MENU_NAV.route) {
                    popUpTo(ComposableRoutes.MAIN_MENU_NAV.route) { inclusive = false }
                    launchSingleTop = true
                }
                else navController.navigate(ComposableRoutes.AUTHENTICATION_SCREEN_NAV.route)
            }
        }
        timer.start()
    }
    else {
        val scope = rememberCoroutineScope()

        // tied to the lifecycle of SplashScreen, if it is recomposed this will not restart
        LaunchedEffect(true) {
            scope.launch(Dispatchers.Main) {
                loadContent()
                navController.navigate(ComposableRoutes.GAME_SCREEN_NAV.route) {
                    popUpTo(ComposableRoutes.GAME_SCREEN_NAV.route) { inclusive = false }
                    launchSingleTop = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.menu_bg),
            contentDescription = stringResource(id = com.huntergaming.authentication.R.string.content_description_not_needed),
            contentScale = ContentScale.FillBounds
        )

        ClassicSolitaireTitle()
    }
}

// previews

@Preview(showBackground = true, widthDp = 1280, heightDp = 720)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme {
        SplashScreen(
            navController = NavHostController(LocalContext.current),
            authViewModel = AuthenticationViewModel(null, LocalContext.current)
        )
    }
}

@Preview(showBackground = true, widthDp = 1920, heightDp = 1080)
@Composable
private fun DefaultPreview2() {
    ClassicSolitaireTheme {
        SplashScreen(
            navController = NavHostController(LocalContext.current),
            authViewModel = AuthenticationViewModel(null, LocalContext.current)
        )
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 480)
@Composable
private fun DefaultPreview3() {
    ClassicSolitaireTheme {
        SplashScreen(
            navController = NavHostController(LocalContext.current),
            authViewModel = AuthenticationViewModel(null, LocalContext.current)
        )
    }
}

@Preview(showBackground = true, widthDp = 854, heightDp = 480)
@Composable
private fun DefaultPreview4() {
    ClassicSolitaireTheme {
        SplashScreen(
            navController = NavHostController(LocalContext.current),
            authViewModel = AuthenticationViewModel(null, LocalContext.current)
        )
    }
}