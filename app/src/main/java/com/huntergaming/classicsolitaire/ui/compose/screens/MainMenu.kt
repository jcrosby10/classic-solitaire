package com.huntergaming.classicsolitaire.ui.compose.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.ExitToApp
import androidx.compose.material.icons.twotone.PlayCircle
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.compose.ClassicSolitaireTitle
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.ui.composable.HunterGamingBackgroundImage
import com.huntergaming.ui.composable.HunterGamingButton
import kotlin.system.exitProcess

private const val USER_QUIT = 0

// composables

@Composable
internal fun MainMenu(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        HunterGamingBackgroundImage(
            image = R.drawable.menu_bg
        )

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        screenHeight.value

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ClassicSolitaireTitle(
                scale = screenHeight.value / 2 * .001f,
                modifier = Modifier
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                HunterGamingButton(
                    onClick = { navController.navigate(ComposableRoutes.SETTINGS_MENU_NAV.route) },
                    icon = if (isSystemInDarkTheme()) Icons.TwoTone.Settings else Icons.Outlined.Settings,
                    contentDescription = R.string.content_description_settings
                )

                HunterGamingButton(
                    onClick = {  },
                    icon = if (isSystemInDarkTheme()) Icons.TwoTone.PlayCircle else Icons.Outlined.PlayCircle,
                    contentDescription = R.string.content_description_play,
                    modifier = Modifier
                        .scale(2f)
                )

                HunterGamingButton(
                    onClick = { exitProcess(USER_QUIT) },
                    icon = if (isSystemInDarkTheme()) Icons.TwoTone.ExitToApp else Icons.Outlined.ExitToApp,
                    contentDescription = R.string.content_description_quit
                )
            }
        }
    }
}

// previews

@Preview(showBackground = true, widthDp = 1280, heightDp = 720, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    ClassicSolitaireTheme {
        MainMenu(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, widthDp = 1920, heightDp = 1080)
@Composable
private fun DefaultPreview2() {
    ClassicSolitaireTheme {
        MainMenu(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 480, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview3() {
    ClassicSolitaireTheme {
        MainMenu(
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, widthDp = 854, heightDp = 480)
@Composable
private fun DefaultPreview4() {
    ClassicSolitaireTheme {
        MainMenu(
            navController = rememberNavController()
        )
    }
}