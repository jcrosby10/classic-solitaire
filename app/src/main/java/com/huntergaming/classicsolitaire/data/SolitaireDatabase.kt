package com.huntergaming.classicsolitaire.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.huntergaming.classicsolitaire.data.model.Games
import com.huntergaming.classicsolitaire.data.model.Player

@Database(entities = [Player::class, Games::class], version = 1)
abstract class SolitaireDatabase: RoomDatabase() {
    abstract fun getPlayerDao(): PlayerDao
    abstract fun getGamesDao(): GamesDao

    companion object {
        @Volatile
        private var INSTANCE: SolitaireDatabase? = null

        fun getDatabase(context: Context): SolitaireDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SolitaireDatabase::class.java,
                    "classic_solitaire_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}