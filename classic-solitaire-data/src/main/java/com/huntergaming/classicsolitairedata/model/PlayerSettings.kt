package com.huntergaming.classicsolitairedata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_settings")
data class PlayerSettings(

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "store_in_cloud", typeAffinity = ColumnInfo.TEXT)
    var storeInCloud: String,

    @ColumnInfo(name = "audio_on", typeAffinity = ColumnInfo.TEXT)
    var audioOn: String,

    @ColumnInfo(name = "audio_value", typeAffinity = ColumnInfo.REAL)
    var audioValue: Float,

    @ColumnInfo(name = "sfx_on", typeAffinity = ColumnInfo.TEXT)
    var sfxOn: String,

    @ColumnInfo(name = "sfx_value", typeAffinity = ColumnInfo.REAL)
    var sfxValue: Float,

    @ColumnInfo(name = "number_of_decks", typeAffinity = ColumnInfo.INTEGER)
    var numberOfDecks: Int,

    @ColumnInfo(name = "deck_background", typeAffinity = ColumnInfo.INTEGER)
    var deckBackground: Int
)
