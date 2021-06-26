package com.huntergaming.classicsolitaire.repository

import com.huntergaming.classicsolitaire.data.ClassicSolitaireCache
import com.huntergaming.classicsolitaire.data.GamesDao
import com.huntergaming.classicsolitaire.data.PlayerDao
import com.huntergaming.classicsolitaire.data.model.Games
import com.huntergaming.classicsolitaire.data.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SolitaireRepository @Inject constructor(
    private val gamesDao: GamesDao,
    private val playerDao: PlayerDao,
    private val playerCache: ClassicSolitaireCache<String, Player>,
    private val allGamesCache: ClassicSolitaireCache<String, List<Games>>,
    private val topTenGamesCache: ClassicSolitaireCache<String, List<Games>>
) {

    private val allGamesKey = "all_games"
    private val topTenKey = "top_ten_games"
    private val playerKey = "player"

    suspend fun getAllGames(): Flow<List<Games>> {
        if (allGamesCache.contains(allGamesKey)) {
            return flowOf(allGamesCache.get(allGamesKey))
        }
        return gamesDao.getAllGames()
    }

    suspend fun getTopTenGames(): Flow<List<Games>> {
        if (topTenGamesCache.contains(topTenKey)) {
            return flowOf(topTenGamesCache.get(topTenKey))
        }
        return gamesDao.getTopTenGames()
    }

    suspend fun getPlayer(): Flow<Player> {
        if (playerCache.contains(playerKey)) {
            return flowOf(playerCache.get(playerKey))
        }
        return playerDao.getPlayer()
    }

    suspend fun insert(player: Player) = playerDao.insert(player)
    suspend fun update(player: Player) = playerDao.update(player)
}