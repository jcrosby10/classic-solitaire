package com.huntergaming.classicsolitaire.ui.compose.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme

@Preview(showBackground = true, widthDp = 720, heightDp = 360)
@Composable
private fun DefaultPreviewGameScreen() {
    ClassicSolitaireTheme {
        GameScreen()
    }
}

@Composable
internal fun GameScreen() {

    // redirect to login if player is not logged in
    // audio FloatingActionButton

    // save each change, if player leaves for extended time and comes back
    // save game in progress to data store
    // make splash screen check for in progress and load most recent game
}