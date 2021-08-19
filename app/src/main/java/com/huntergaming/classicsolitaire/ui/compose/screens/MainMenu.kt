package com.huntergaming.classicsolitaire.ui.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.huntergaming.classicsolitaire.R
import com.huntergaming.classicsolitaire.ui.compose.ClassicSolitaireButton
import com.huntergaming.classicsolitaire.ui.compose.ClassicSolitaireHeaderText
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreviewMainMenu() {
    ClassicSolitaireTheme {
        MainMenu()
    }
}

@Composable
internal fun MainMenu() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val (mainMenu, playButton, gameName) = createRefs()

        ClassicSolitaireHeaderText(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.edge_padding_15dp))
                .constrainAs(gameName) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(gameName.top)
                },
            text = R.string.app_name
        )

        Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.edge_padding_5dp))
            .constrainAs(mainMenu) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            ClassicSolitaireButton(
                onClick = {  },
                text = R.string.button_settings
            )

            ClassicSolitaireButton(
                onClick = {  },
                text = R.string.button_quit
            )
        }

        ClassicSolitaireButton(
            onClick = {  },
            text = R.string.button_play,
            modifier = Modifier
                .constrainAs(playButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}