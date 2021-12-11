package com.huntergaming.classicsolitairedata

import com.huntergaming.classicsolitairedata.dao.PlayerSettingsFirebaseDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.gamedata.DataRequestState
import com.huntergaming.gamedata.dao.HunterGamingDao
import com.huntergaming.gamedata.preferences.FirebasePreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClassicSolitaireRepository @Inject constructor(
    private val firebasePreferences: FirebasePreferences,
    private val playerSettingsDao: HunterGamingDao<PlayerSettings>,
    private val playerSettingsFirebaseDao: PlayerSettingsFirebaseDao
) : PlayerSettingsRepo {

    override suspend fun getPlayerSettings(): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)

        runCatching {
            val settings =
                if (firebasePreferences.canUseFirebase()) playerSettingsFirebaseDao.getPlayerSettings()
                else playerSettingsDao.read()

            emit(DataRequestState.Success(settings))
        }
            .getOrElse { emit(DataRequestState.Error(it.message)) }
    }

    override suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)
        runCatching {
            if (firebasePreferences.canUseFirebase()) playerSettingsFirebaseDao.updatePlayerSettings(playerSettings)
            else playerSettingsDao.update(playerSettings)

            emit(DataRequestState.Success(Unit))
        }
            .getOrElse { emit(DataRequestState.Error(it.message)) }
    }
}

interface PlayerSettingsRepo {
    suspend fun getPlayerSettings(): Flow<DataRequestState>
    suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState>
}