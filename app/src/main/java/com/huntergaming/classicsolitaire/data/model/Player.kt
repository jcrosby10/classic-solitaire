package com.huntergaming.classicsolitaire.data.model

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "player")
data class Player @Inject constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name", defaultValue = "", typeAffinity = TEXT) val name: String,
    @ColumnInfo(name = "games_played", defaultValue = "0", typeAffinity = ColumnInfo.INTEGER) val gamesPlayed: Int,
    @ColumnInfo(name = "average_score", defaultValue = "0", typeAffinity = ColumnInfo.INTEGER) val averageScore: Int
)