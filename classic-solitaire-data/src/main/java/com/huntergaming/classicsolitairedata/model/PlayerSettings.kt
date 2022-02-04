package com.huntergaming.classicsolitairedata.model

data class PlayerSettings(

    var id: Int,
    var audioOn: String,
    var audioValue: Float,
    var sfxOn: String,
    var sfxValue: Float,
    var numberOfDecks: Int,
    var deckBackground: Int
)
