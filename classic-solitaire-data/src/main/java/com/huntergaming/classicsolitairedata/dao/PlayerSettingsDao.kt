package com.huntergaming.classicsolitairedata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.gamedata.dao.HunterGamingMigrateDao
import com.huntergaming.gamedata.dao.RoomDao
import javax.inject.Singleton

@Singleton
@Dao
internal interface PlayerSettingsDao : RoomDao<PlayerSettings>, HunterGamingMigrateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun create(data: PlayerSettings): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(data: PlayerSettings): Int

    @Query("SELECT * FROM player_settings")
    override suspend fun read(): PlayerSettings

    override suspend fun migrateData() {
        // migrate then delete
        TODO("Migrate from Room to Firestore")
    }
}