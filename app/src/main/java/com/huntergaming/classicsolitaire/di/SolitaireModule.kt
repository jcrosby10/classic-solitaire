package com.huntergaming.classicsolitaire.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.huntergaming.gamedata.data.Authentication
import com.huntergaming.gamedata.data.Dao
import com.huntergaming.gamedata.data.FirebaseAuthentication
import com.huntergaming.gamedata.data.PlayerDao
import com.huntergaming.gamedata.data.model.Player
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SolitaireModule {


}