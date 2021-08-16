package com.huntergaming.classicsolitaire.ui.compose.screens

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.huntergaming.classicsolitaire.BuildConfig
import com.huntergaming.classicsolitaire.GAME_SCREEN_NAV_ROUTE
import com.huntergaming.classicsolitaire.MAIN_MENU_NAV_ROUTE
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.compose.ClassicSolitaireHeaderText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val time: Long = if (BuildConfig.DEBUG) 1000 else 5000

@Composable
internal fun SplashScreen(loadContent: (suspend () -> Unit)? = null, navController: NavHostController) {
    if (loadContent == null) {
        val timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() { navController.navigate(MAIN_MENU_NAV_ROUTE) }
        }
        timer.start()
    }
    else {
        val scope = rememberCoroutineScope()

        // tied to the lifecycle of SplashScreen, if it is recomposed this will not restart
        LaunchedEffect(true) {
            scope.launch(Dispatchers.Main) {
                loadContent()
                navController.navigate(GAME_SCREEN_NAV_ROUTE)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center,
    ) {
        ClassicSolitaireHeaderText(text = R.string.app_name)
    }
}