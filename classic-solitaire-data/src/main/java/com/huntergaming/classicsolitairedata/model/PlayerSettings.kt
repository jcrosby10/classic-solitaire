package com.huntergaming.classicsolitairedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_settings")
data class PlayerSettings(

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    @PrimaryKey(autoGenerate = false)
    var id: String,

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, name = "audio_on")
    var audioOn: String,

    @ColumnInfo(typeAffinity = ColumnInfo.REAL, name = "audio_value")
    var audioValue: Float,

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, name = "sfx_on")
    var sfxOn: String,

    @ColumnInfo(typeAffinity = ColumnInfo.REAL, name = "sfx_value")
    var sfxValue: Float,

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER, name = "number_of_decks")
    var numberOfDecks: Int,

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER, name = "deck_background")
    var deckBackground: Int
)
