package com.huntergaming.classicsolitaire.model

import javax.inject.Inject

/**
 * Data class for the player.
 */
data class Player @Inject constructor(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gamesPlayed: Int,
    val averageScore: Int
)