package com.huntergaming.classicsolitaire.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.huntergaming.authentication.Authentication
import com.huntergaming.gamedata.DataRequestState
import com.huntergaming.gamedata.PlayerRepo
import com.huntergaming.web.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepo: PlayerRepo,
    private val hunterGamingAuth: Authentication,
    @ApplicationContext private val context: Context// application not activity
): ViewModel() {

    // functions

    suspend fun getPlayerName(): LiveData<DataRequestState> = liveData {

        if (!isConnected(context)) emit(DataRequestState.NoInternet)

        withContext(Dispatchers.IO) {
            playerRepo.getPlayer(hunterGamingAuth.user?.uid!!).collect { state ->
                emit(state)
            }
        }
    }
}