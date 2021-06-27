package com.huntergaming.classicsolitaire.model

import javax.inject.Inject

/**
 * Data class for a game.
 */
data class Game @Inject constructor(
    val id: Int,
    val score: Int
)