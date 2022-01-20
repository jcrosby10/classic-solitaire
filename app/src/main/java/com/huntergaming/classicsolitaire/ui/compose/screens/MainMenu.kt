package com.huntergaming.classicsolitaire.ui.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.huntergaming.classicsolitaire.ComposableRoutes
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme
import com.huntergaming.ui.composable.HunterGamingButton
import com.huntergaming.ui.composable.HunterGamingHeaderText
import kotlin.system.exitProcess

private const val USER_QUIT = 0

@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
private fun DefaultPreviewMainMenu() {
    ClassicSolitaireTheme {
        MainMenu(
            navController = rememberNavController()
        )
    }
}

@Composable
internal fun MainMenu(
    navController: NavHostController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val (mainMenu, playButton, gameName) = createRefs()

        HunterGamingHeaderText(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.padding_large))
                .constrainAs(ref = gameName) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            text = R.string.app_name
        )

        Column(
            modifier = Modifier
                .constrainAs(ref = mainMenu) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(
                    start = dimensionResource(R.dimen.padding_large)
                )
        ) {
            HunterGamingButton(
                onClick = { navController.navigate(ComposableRoutes.SETTINGS_MENU_NAV.route) },
                text = R.string.button_settings,
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_medium))
            )

            HunterGamingButton(
                onClick = { exitProcess(USER_QUIT) },
                text = R.string.button_quit,
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))
            )
        }

        HunterGamingButton(
            onClick = {  },
            text = R.string.button_play,
            modifier = Modifier
                .constrainAs(ref = playButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}