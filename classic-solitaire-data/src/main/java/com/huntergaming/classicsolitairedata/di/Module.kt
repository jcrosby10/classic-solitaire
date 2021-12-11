package com.huntergaming.classicsolitairedata.di

import com.huntergaming.classicsolitairedata.ClassicSolitaireDatabase
import com.huntergaming.classicsolitairedata.ClassicSolitaireRepository
import com.huntergaming.classicsolitairedata.PlayerSettingsRepo
import com.huntergaming.classicsolitairedata.dao.ClassicSolitaireFirebaseDao
import com.huntergaming.classicsolitairedata.dao.PlayerSettingsFirebaseDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.gamedata.dao.HunterGamingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun providePlayerSettingsRepo(repository: ClassicSolitaireRepository): PlayerSettingsRepo = repository

    @Provides
    internal fun provideHunterGamingDao(db: ClassicSolitaireDatabase): HunterGamingDao<PlayerSettings> = db.getPlayerSettingsDao()

    @Provides
    internal fun providePlayerSettingsFirebaseDao(firebaseDao: ClassicSolitaireFirebaseDao): PlayerSettingsFirebaseDao = firebaseDao
}