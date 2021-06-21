package com.huntergaming.classicsolitaire.repository

import com.huntergaming.classicsolitaire.data.GamesDao
import com.huntergaming.classicsolitaire.data.PlayerDao
import com.huntergaming.classicsolitaire.data.model.Player
import javax.inject.Inject

class SolitaireRepository @Inject constructor(
    gamesDao: GamesDao,
    private val playerDao: PlayerDao
) {

    val allGames = gamesDao.getAllGames()
    val topTenGames = gamesDao.getTopTenGames()

    val player = playerDao.getPlayer()

    fun insert(player: Player) = playerDao.insert(player)
    fun update(player: Player) = playerDao.update(player)
}