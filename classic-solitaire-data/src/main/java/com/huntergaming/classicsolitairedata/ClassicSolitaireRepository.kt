package com.huntergaming.classicsolitairedata

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseException
import com.huntergaming.classicsolitairedata.dao.PlayerSettingsFirebaseDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.ui.uitl.DataRequestState
import com.huntergaming.ui.uitl.CommunicationAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClassicSolitaireRepository @Inject constructor(
    private val playerSettingsFirebaseDao: PlayerSettingsFirebaseDao,
    private val communicationAdapter: CommunicationAdapter,
    @ApplicationContext private val context: Context
) : PlayerSettingsRepo {

    // companion objects

    companion object {
        private const val LOG_TAG = "ClassicSolitaireRepository"
    }

    // overridden functions

    override suspend fun getPlayerSettings(): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)

        try {
            val settings = playerSettingsFirebaseDao.getPlayerSettings()
            emit(DataRequestState.Success(settings))
        }
        catch (e: FirebaseException) {
            Log.e(LOG_TAG, e.message, e)
            communicationAdapter.message.value?.add(context.getString(R.string.error_get_settings))
        }
    }

    override suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)

        try {
            playerSettingsFirebaseDao.updatePlayerSettings(playerSettings)
            emit(DataRequestState.Success(Unit))
        }
        catch (e: FirebaseException) {
            Log.e(LOG_TAG, e.message, e)
            communicationAdapter.message.value?.add(context.getString(R.string.error_update_settings))
        }
    }
}

// interfaces

interface PlayerSettingsRepo {
    suspend fun getPlayerSettings(): Flow<DataRequestState>
    suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState>
}