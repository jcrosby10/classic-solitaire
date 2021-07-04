package com.huntergaming.classicsolitaire.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(
        topStart = 10.dp,
        topEnd = 20.dp,
        bottomStart = 10.dp,
        bottomEnd = 20.dp
    ),
    medium = RoundedCornerShape(
        topStart = 15.dp,
        topEnd = 30.dp,
        bottomStart = 15.dp,
        bottomEnd = 30.dp
    ),
    large = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 40.dp,
        bottomStart = 20.dp,
        bottomEnd = 40.dp
    )
)