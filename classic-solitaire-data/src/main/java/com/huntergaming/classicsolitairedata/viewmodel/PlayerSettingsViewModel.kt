package com.huntergaming.classicsolitairedata.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.huntergaming.classicsolitairedata.PlayerSettingsRepo
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.ui.uitl.DataRequestState
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

class PlayerSettingsViewModel @Inject constructor(
    private val playerSettingsRepo: PlayerSettingsRepo
): ViewModel() {

    // functions

    fun getNumberOfDecks() = liveData {
        playerSettingsRepo.getPlayerSettings().collect {
            when (it) {
                is DataRequestState.Success<*> -> emit(DataRequestState.Success((it.data as PlayerSettings).numberOfDecks))
                DataRequestState.InProgress -> emit(it)
            }
        }
    }

    fun getDeckBackground() = liveData {
        playerSettingsRepo.getPlayerSettings().collect {
            when (it) {
                is DataRequestState.Success<*> -> emit(DataRequestState.Success((it.data as PlayerSettings).deckBackground))
                DataRequestState.InProgress -> emit(it)
            }
        }
    }

    fun getAudio() = liveData {
        playerSettingsRepo.getPlayerSettings().collect {
            when (it) {
                is DataRequestState.Success<*> -> emit(DataRequestState.Success((it.data as PlayerSettings).audioValue))
                DataRequestState.InProgress -> emit(it)
            }
        }
    }

    fun getAudioOn() = liveData {
        playerSettingsRepo.getPlayerSettings().collect {
            when (it) {
                is DataRequestState.Success<*> -> emit(DataRequestState.Success((it.data as PlayerSettings).audioOn))
                DataRequestState.InProgress -> emit(it)
            }
        }
    }

    fun getSfx() = liveData {
        playerSettingsRepo.getPlayerSettings().collect {
            when (it) {
                is DataRequestState.Success<*> -> emit(DataRequestState.Success((it.data as PlayerSettings).sfxValue))
                DataRequestState.InProgress -> emit(it)
            }
        }
    }

    fun getSfxOn() = liveData {
        playerSettingsRepo.getPlayerSettings().collect {
            when (it) {
                is DataRequestState.Success<*> -> emit(DataRequestState.Success((it.data as PlayerSettings).sfxOn))
                DataRequestState.InProgress -> emit(it)
            }
        }
    }
}