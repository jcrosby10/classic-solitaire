package com.huntergaming.classicsolitaire.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.huntergaming.classicsolitaire.R

private val AntiCoronaBoldTitle = FontFamily(
    Font(R.font.anti_corona, FontWeight.Bold)
)

private val AntiCoronaTitle = FontFamily(
    Font(R.font.anti_corona, FontWeight.Normal, FontStyle.Italic)
)

private val Colombia = FontFamily(
    Font(R.font.colombia, FontWeight.Normal)
)

private val ColombiaBold = FontFamily(
    Font(R.font.colombia, FontWeight.Bold)
)

internal val LightTypography = Typography(
    h1 = TextStyle(
        fontFamily = AntiCoronaBoldTitle,
        fontSize = 34.sp,
        letterSpacing = 2.sp,
        shadow = Shadow(BackgroundDark, Offset(.8f, .8f), .5f),
        color = OnPrimaryLight
    ),
    h4 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 30.sp,
        letterSpacing = 1.5.sp,
        shadow = Shadow(BackgroundDark, Offset(.4f, .2f), .3f),
        color = OnPrimaryLight
    ),
    h6 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 20.sp,
        color = OnPrimaryLight
    ),
    body1 = TextStyle(
        fontFamily = Colombia,
        fontSize = 18.sp,
        color = OnPrimaryLight
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.25.sp,
        color = OnPrimaryLight
    ),
    caption = TextStyle(
        fontFamily = Colombia,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = OnPrimaryLight
    ),
    button = TextStyle(
        fontFamily = ColombiaBold,
        fontSize = 25.sp,
        textDecoration = TextDecoration.Underline,
        color = OnPrimaryLight
    )
)

internal val DarkTypography = Typography(
    h1 = TextStyle(
        fontFamily = AntiCoronaBoldTitle,
        fontSize = 34.sp,
        letterSpacing = 2.sp,
        shadow = Shadow(BackgroundDark, Offset(.8f, .8f), .5f),
        color = OnPrimaryDark
    ),
    h4 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 30.sp,
        letterSpacing = 1.5.sp,
        shadow = Shadow(BackgroundDark, Offset(.4f, .2f), .3f),
        color = OnPrimaryDark
    ),
    h6 = TextStyle(
        fontFamily = AntiCoronaTitle,
        fontSize = 20.sp,
        color = OnPrimaryDark
    ),
    body1 = TextStyle(
        fontFamily = Colombia,
        fontSize = 18.sp,
        color = OnPrimaryDark
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.25.sp,
        color = OnPrimaryDark
    ),
    caption = TextStyle(
        fontFamily = Colombia,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        color = OnPrimaryDark
    ),
    button = TextStyle(
        fontFamily = ColombiaBold,
        fontSize = 25.sp,
        textDecoration = TextDecoration.Underline,
        color = OnPrimaryDark
    )
)