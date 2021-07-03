package com.huntergaming.classicsolitaire.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    onPrimary = OnDark,
    primaryVariant = VariantDark,
    secondary = SecondaryDark,
    onSecondary = OnDark,
    background = BackgroundDark,
    onBackground = OnDark,
    error = ErrorDark,
    onError = OnDark
)

private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    onPrimary = OnLight,
    primaryVariant = VariantLight,
    secondary = SecondaryLight,
    onSecondary = OnLight,
    background = BackgroundLight,
    onBackground = OnLight,
    error = ErrorLight,
    onError = OnLight
)

@Composable
fun ClassicSolitaireTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    }
    else {
        LightColorPalette
    }

    val typography = if (darkTheme) {
        DarkTypography
    }
    else {
        LightTypography
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}