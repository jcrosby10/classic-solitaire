package com.huntergaming.classicsolitairedata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huntergaming.classicsolitairedata.dao.PlayerSettingsRoomDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import javax.inject.Singleton

@Singleton
@Database(entities = [PlayerSettings::class], version = 1)
internal abstract class ClassicSolitaireDatabase: RoomDatabase() {
    abstract fun getPlayerSettingsDao(): PlayerSettingsRoomDao
}