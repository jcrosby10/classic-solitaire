package com.huntergaming.classicsolitairedata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.gamedata.dao.HunterGamingDao
import com.huntergaming.gamedata.dao.HunterGamingMigrateDao
import javax.inject.Singleton

@Singleton
@Dao
internal interface PlayerSettingsDao : HunterGamingDao<PlayerSettings>, HunterGamingMigrateDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun create(data: PlayerSettings)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(data: PlayerSettings)

    @Query("SELECT * FROM player_settings")
    override suspend fun read(): PlayerSettings

    override suspend fun migrateDataToFirestore() {
        TODO("Migrate from Room to Firestore")
    }

    override suspend fun migrateDataToRoom() {
        TODO("Migrate data from Firestore to Room")
    }
}