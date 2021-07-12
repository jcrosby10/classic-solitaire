package com.huntergaming.classicsolitaire.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.huntergaming.classicsolitaire.model.Player
import com.huntergaming.classicsolitaire.web.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PlayerDao @Inject constructor(
    private val firestore: FirebaseFirestore
): Dao<Player> {

    override suspend fun create(data: Player): Flow<RequestState> = callbackFlow {
        firestore.collection(data.id).document("PlayerData").set(data)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }

        awaitClose { cancel() }
    }

    override fun update(data: Player) {

    }

    override fun get(): Player {
        TODO("Not yet implemented")
    }

    override fun delete() {

    }
}