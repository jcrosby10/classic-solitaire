package com.huntergaming.classicsolitaire.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.huntergaming.classicsolitaire.model.Player
import javax.inject.Inject

class PlayerDao @Inject constructor(
    private val firestore: FirebaseFirestore
): Dao<Player> {

    override fun create(data: Player) {
        firestore.collection(data.id).document("PlayerData").set(data)
    }

    override fun update(data: Player) {

    }

    override fun get(): Player {
        TODO("Not yet implemented")
    }

    override fun delete() {

    }
}