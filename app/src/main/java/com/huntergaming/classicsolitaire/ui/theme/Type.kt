package com.huntergaming.classicsolitaire.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
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

internal val DarkTypography = Typography(
    h1 = TextStyle(
        fontFamily = AntiCoronaBoldTitle,
        fontSize = 34.sp,
        letterSpacing = 2.sp,
        shadow = Shadow(BackgroundDark, Offset(.8f, .8f), .5f),
        color = OnDark
    ),
    h4 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 30.sp,
        letterSpacing = 1.5.sp,
        shadow = Shadow(BackgroundDark, Offset(.4f, .2f), .3f),
        color = OnDark
    ),
    h6 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 20.sp,
        color = OnDark
    ),
    body1 = TextStyle(
        fontFamily = Colombia,
        fontSize = 18.sp,
        color = OnDark
    ),
    button = TextStyle(
        fontFamily = ColombiaBold,
        fontSize = 25.sp,
        textDecoration = TextDecoration.Underline,
        color = OnDark
    )
)

internal val LightTypography = Typography(
    h1 = TextStyle(
        fontFamily = AntiCoronaBoldTitle,
        fontSize = 34.sp,
        letterSpacing = 2.sp,
        shadow = Shadow(BackgroundLight, Offset(.8f, .8f), .5f),
        color = OnLight
    ),
    h4 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 30.sp,
        letterSpacing = 1.5.sp,
        shadow = Shadow(BackgroundLight, Offset(.4f, .2f), .3f),
        color = OnLight
    ),
    h6 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 20.sp,
        color = OnLight
    ),
    body1 = TextStyle(
        fontFamily = Colombia,
        fontSize = 16.sp,
        color = OnLight
    ),
    button = TextStyle(
        fontFamily = ColombiaBold,
        fontSize = 14.sp,
        textDecoration = TextDecoration.Underline,
        color = OnLight
    )
)