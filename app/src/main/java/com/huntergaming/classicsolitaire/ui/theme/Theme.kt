package com.huntergaming.classicsolitaire.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = PrimaryVariantDark,
    secondary = SecondaryDark,
    secondaryVariant = SecondaryVariantDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    error = ErrorDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark,
    onError = OnErrorDark
)

private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    primaryVariant = PrimaryVariantLight,
    secondary = SecondaryLight,
    secondaryVariant = SecondaryVariantLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = ErrorLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight,
    onError = OnErrorLight
)

@Composable
internal fun ClassicSolitaireTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = if (darkTheme) DarkTypography else LightTypography,
        shapes = Shapes,
        content = content
    )
}