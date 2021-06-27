package com.huntergaming.classicsolitaire.model

import javax.inject.Inject

/**
 * Data class for the player.
 */
data class Player @Inject constructor(
    val id: Int,
    val name: String,
    val gamesPlayed: Int,
    val averageScore: Int
)