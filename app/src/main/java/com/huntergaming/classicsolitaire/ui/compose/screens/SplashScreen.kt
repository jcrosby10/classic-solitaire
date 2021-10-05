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
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.BuildConfig
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.navFlow
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.composables.HunterGamingHeaderText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val time: Long = if (BuildConfig.DEBUG) 1000 else 5000

@Preview(showBackground = true)
@Composable
private fun DefaultPreviewSplashScreen() {
    ClassicSolitaireTheme {
        SplashScreen()
    }
}

@Composable
internal fun SplashScreen(loadContent: (suspend () -> Unit)? = null) {
    if (loadContent == null) {
        val timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() { navFlow.value = ComposableRoutes.MAIN_MENU_NAV.route }
        }
        timer.start()
    }
    else {
        val scope = rememberCoroutineScope()

        // tied to the lifecycle of SplashScreen, if it is recomposed this will not restart
        LaunchedEffect(true) {
            scope.launch(Dispatchers.Main) {
                loadContent()
                navFlow.value = ComposableRoutes.GAME_SCREEN_NAV.route
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center,
    ) {
        HunterGamingHeaderText(
            modifier = Modifier,
            text = R.string.app_name
        )
    }
}