package com.huntergaming.classicsolitairedata

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseException
import com.huntergaming.classicsolitairedata.dao.ClassicSolitaireMigrateDao
import com.huntergaming.classicsolitairedata.dao.ClassicSolitaireRoomDao
import com.huntergaming.classicsolitairedata.dao.PlayerSettingsFirebaseDao
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.ui.uitl.DataRequestState
import com.huntergaming.ui.uitl.CommunicationAdapter
import com.huntergaming.web.InternetConnectionStatus
import com.huntergaming.web.InternetStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ClassicSolitaireRepository @Inject constructor(
    private val playerSettingsFirebaseDao: PlayerSettingsFirebaseDao,
    private val playerSettingsRoomDao: ClassicSolitaireRoomDao<PlayerSettings>,
    private val playerSettingsMigrateDao: ClassicSolitaireMigrateDao<PlayerSettings>,
    private val communicationAdapter: CommunicationAdapter,
    @ApplicationContext private val context: Context,
    private val internetStatus: InternetStatus,
) : PlayerSettingsRepo {

    // companion objects

    companion object {
        private const val LOG_TAG = "ClassicSolitaireRepository"
    }

    // properties

    private var previouslyDisconnected = false
    private var saveInRoom = true

    // initializers

    init {
        CoroutineScope(Dispatchers.Default).launch {
            internetStatus.internetConnectionStatus.collect {
                when (it) {
                    InternetConnectionStatus.UNKNOWN -> {}

                    InternetConnectionStatus.CONNECTED -> {
                        saveInRoom = false

                        if (previouslyDisconnected) {
                            previouslyDisconnected = false

                            try {
                                val settings = playerSettingsMigrateDao.getAll()
                                for (setting in settings) playerSettingsFirebaseDao.updatePlayerSettings(setting)

                                playerSettingsMigrateDao.deleteAll()
                            }
                            catch (e: Exception) {
                                Log.e(LOG_TAG, e.message, e)
                                communicationAdapter.message.value?.add(context.getString(R.string.error_migrating_data))
                            }
                        }
                    }

                    InternetConnectionStatus.DISCONNECTED -> {
                        previouslyDisconnected = true
                        saveInRoom = true
                    }
                }
            }
        }
    }

    // overridden functions

    override suspend fun getPlayerSettings(): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)

        try {
            if (saveInRoom) {
                val settings = playerSettingsRoomDao.read()
                emit(DataRequestState.Success(settings))
            }
            else {
                val settings = playerSettingsFirebaseDao.getPlayerSettings()
                emit(DataRequestState.Success(settings))
            }
        }
        catch (e: FirebaseException) {
            Log.e(LOG_TAG, e.message, e)
            communicationAdapter.message.value?.add(context.getString(R.string.error_get_settings))
        }
    }

    override suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Flow<DataRequestState> = flow {
        emit(DataRequestState.InProgress)

        try {
            if (saveInRoom) {
                val result = playerSettingsRoomDao.update(playerSettings)
                emit(DataRequestState.Success(result > 0))
            }
            else {
                val result = playerSettingsFirebaseDao.updatePlayerSettings(playerSettings)
                emit(DataRequestState.Success(result))
            }
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