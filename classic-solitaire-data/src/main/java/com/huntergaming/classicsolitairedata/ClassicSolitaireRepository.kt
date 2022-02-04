package com.huntergaming.classicsolitairedata

import com.huntergaming.classicsolitairedata.dao.PlayerSettingsFirebaseDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.gamedata.DataRequestState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClassicSolitaireRepository @Inject constructor(
    private val playerSettingsFirebaseDao: PlayerSettingsFirebaseDao
) : PlayerSettingsRepo {

    // overridden functions

    override suspend fun getPlayerSettings(): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)

        runCatching {
            
            val settings = playerSettingsFirebaseDao.getPlayerSettings()
            emit(DataRequestState.Success(settings))
        }
            .getOrElse { emit(DataRequestState.Error(it.message ?: "")) }
    }

    override suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)
        runCatching {

            playerSettingsFirebaseDao.updatePlayerSettings(playerSettings)
            emit(DataRequestState.Success(Unit))
        }
            .getOrElse { emit(DataRequestState.Error(it.message ?: "")) }
    }
}

// interfaces

interface PlayerSettingsRepo {
    suspend fun getPlayerSettings(): Flow<DataRequestState>
    suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState>
}