package com.huntergaming.classicsolitairedata.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import javax.inject.Singleton

@Singleton
@Dao
internal interface PlayerSettingsRoomDao: ClassicSolitaireRoomDao<PlayerSettings>, ClassicSolitaireMigrateDao<PlayerSettings> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun create(data: PlayerSettings): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun update(data: PlayerSettings): Int

    @Query("SELECT * FROM player_settings")
    override suspend fun read(): PlayerSettings

    @Query("SELECT * FROM player_settings")
    override fun observe(): LiveData<PlayerSettings>

    @Query("SELECT * FROM player_settings")
    override fun getAll(): List<PlayerSettings>

    @Query("DELETE FROM player_settings")
    override fun deleteAll()
}

interface ClassicSolitaireMigrateDao<T> {
    fun getAll(): List<T>
    fun deleteAll()
}

interface ClassicSolitaireRoomDao<T> {
    suspend fun create(data: T): Long
    suspend fun update(data: T): Int
    suspend fun read(): T
    fun observe(): LiveData<T>
}