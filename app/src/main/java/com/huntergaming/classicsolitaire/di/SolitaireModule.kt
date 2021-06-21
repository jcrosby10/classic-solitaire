package com.huntergaming.classicsolitaire.di

import android.content.Context
import com.huntergaming.classicsolitaire.data.GamesDao
import com.huntergaming.classicsolitaire.data.PlayerDao
import com.huntergaming.classicsolitaire.data.SolitaireDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SolitaireModule {

    @Singleton
    @Provides
    fun provideGamesDao(db: SolitaireDatabase): GamesDao = db.getGamesDao()

    @Singleton
    @Provides
    fun providePlayerDao(db: SolitaireDatabase): PlayerDao = db.getPlayerDao()

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): SolitaireDatabase = SolitaireDatabase.getDatabase(context)
}