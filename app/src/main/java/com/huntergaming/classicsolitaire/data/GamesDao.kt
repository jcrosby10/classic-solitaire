package com.huntergaming.classicsolitaire.data

import androidx.room.Dao
import androidx.room.Query
import com.huntergaming.classicsolitaire.data.model.Games
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Query("SELECT * FROM games")
    fun getAllGames(): Flow<List<Games>>

    @Query("SELECT * FROM games ORDER BY score ASC LIMIT 10")
    fun getTopTenGames(): Flow<List<Games>>
}