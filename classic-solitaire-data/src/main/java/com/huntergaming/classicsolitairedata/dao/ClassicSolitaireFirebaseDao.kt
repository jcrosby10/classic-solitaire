package com.huntergaming.classicsolitairedata.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import com.huntergaming.gamedata.dao.HunterGamingMigrateDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
internal class ClassicSolitaireFirebaseDao @Inject constructor(
    private val db: FirebaseFirestore
) : HunterGamingMigrateDao,
    PlayerSettingsFirebaseDao {

    override suspend fun getPlayerSettings(): PlayerSettings? = withContext(Dispatchers.IO) {
        var settings: PlayerSettings? = null

        db.collection("").document().get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    settings = it.result?.toObject(PlayerSettings::class.java)
                }
            }

        settings
    }

    override suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Boolean = withContext(Dispatchers.IO) {
        var success = false

        db.collection("").document().set(playerSettings)
            .addOnCompleteListener { success = it.isSuccessful }

        success
    }

    override suspend fun migrateDataToFirestore() {
        TODO("Not yet implemented")
    }

    override suspend fun migrateDataToRoom() {
        TODO("Not yet implemented")
    }
}

interface PlayerSettingsFirebaseDao {
    suspend fun getPlayerSettings(): PlayerSettings?
    suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Boolean
}