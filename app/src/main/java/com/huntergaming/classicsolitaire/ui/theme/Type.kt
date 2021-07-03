package com.huntergaming.classicsolitaire.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.huntergaming.classicsolitaire.R

private val AntiCoronaBoldTitle = FontFamily(
    Font(R.font.anti_corona, FontWeight.Bold)
)

private val AntiCoronaTitle = FontFamily(
    Font(R.font.anti_corona, FontWeight.Bold)
)

private val Colombia = FontFamily(
    Font(R.font.colombia, FontWeight.Normal)
)

private val ColombiaBold = FontFamily(
    Font(R.font.colombia, FontWeight.Bold)
)

// Set of Material typography styles to start with
val LightTypography = Typography(
    h1 = TextStyle(
        fontFamily = AntiCoronaBoldTitle
    ),
    h4 = TextStyle(
        fontFamily = AntiCoronaTitle
    ),
    h6 = TextStyle(
        fontFamily = AntiCoronaTitle
    ),
    body1 = TextStyle(
        fontFamily = Colombia
    ),
    button = TextStyle(
        fontFamily = ColombiaBold
    )
)

val DarkTypography = Typography(
    h1 = TextStyle(
        fontFamily = AntiCoronaBoldTitle
    ),
    h4 = TextStyle(
        fontFamily = AntiCoronaTitle
    ),
    h6 = TextStyle(
        fontFamily = AntiCoronaTitle
    ),
    body1 = TextStyle(
        fontFamily = Colombia
    ),
    button = TextStyle(
        fontFamily = ColombiaBold
    )
)