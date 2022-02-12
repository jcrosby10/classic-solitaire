package com.huntergaming.classicsolitairedata.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.huntergaming.classicsolitairedata.model.PlayerSettings
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

@Singleton
internal class ClassicSolitaireFirebaseDao @Inject constructor(
    private val db: FirebaseFirestore
) : PlayerSettingsFirebaseDao {

    // overridden functions

    override suspend fun getPlayerSettings(): PlayerSettings? = suspendCancellableCoroutine { cont ->
            db.collection("").document().get()

                .addOnCompleteListener {
                    if (it.isSuccessful) {
                       cont.resume(it.result?.toObject(PlayerSettings::class.java))
                    }
                    else cont.resume(null)
                }
        }

    override suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Boolean = suspendCancellableCoroutine { cont ->
        db.collection("").document().set(playerSettings)
            .addOnCompleteListener { cont.resume(it.isSuccessful) }
    }
}

// interfaces

interface PlayerSettingsFirebaseDao {
    suspend fun getPlayerSettings(): PlayerSettings?
    suspend fun updatePlayerSettings(playerSettings: PlayerSettings): Boolean
}