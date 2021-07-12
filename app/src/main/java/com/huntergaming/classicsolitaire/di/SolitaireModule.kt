package com.huntergaming.classicsolitaire.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.huntergaming.classicsolitaire.data.Authentication
import com.huntergaming.classicsolitaire.data.Dao
import com.huntergaming.classicsolitaire.data.FirebaseAuthentication
import com.huntergaming.classicsolitaire.data.PlayerDao
import com.huntergaming.classicsolitaire.model.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SolitaireModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideAuthentication(auth: FirebaseAuthentication): Authentication = auth

    @Singleton
    @Provides
    fun providePlayerDao(dao: PlayerDao): Dao<Player> = dao

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}