package com.huntergaming.classicsolitaire.ui.compose.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.huntergaming.classicsolitaire.ui.theme.ClassicSolitaireTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreviewSettingsScreen() {
    ClassicSolitaireTheme {
        SettingsScreen()
    }
}

@Composable// difficulty (easy,medium,hard), sfx, audio, number of decks (1,2,3), deck background
internal fun SettingsScreen() {// MainMenu background for background color

}