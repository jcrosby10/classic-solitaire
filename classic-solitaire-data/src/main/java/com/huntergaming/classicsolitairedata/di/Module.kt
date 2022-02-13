package com.huntergaming.classicsolitairedata.di

import android.content.Context
import androidx.room.Room
import com.huntergaming.classicsolitairedata.ClassicSolitaireDatabase
import com.huntergaming.classicsolitairedata.ClassicSolitaireRepository
import com.huntergaming.classicsolitairedata.PlayerSettingsRepo
import com.huntergaming.classicsolitairedata.dao.ClassicSolitaireFirebaseDao
import com.huntergaming.classicsolitairedata.dao.ClassicSolitaireRoomDao
import com.huntergaming.classicsolitairedata.dao.PlayerSettingsFirebaseDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun providePlayerSettingsRepo(repository: ClassicSolitaireRepository): PlayerSettingsRepo = repository

    @Provides
    internal fun providePlayerSettingsFirebaseDao(firebaseDao: ClassicSolitaireFirebaseDao): PlayerSettingsFirebaseDao = firebaseDao

    @Provides
    internal fun provideSolitaireDatabase(@ApplicationContext context: Context): ClassicSolitaireDatabase = Room.databaseBuilder(
        context,
        ClassicSolitaireDatabase::class.java,
        "hunter_gaming_database"
    ).build()

    @Provides
    internal fun providePlayerSettingsDao(db: ClassicSolitaireDatabase): ClassicSolitaireRoomDao<PlayerSettings> = db.getPlayerSettingsDao()
}