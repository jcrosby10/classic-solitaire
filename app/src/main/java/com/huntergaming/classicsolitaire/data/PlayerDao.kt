package com.huntergaming.classicsolitaire.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.huntergaming.classicsolitaire.data.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player")
    fun getPlayer(): Flow<Player>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: Player)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(player: Player)
}