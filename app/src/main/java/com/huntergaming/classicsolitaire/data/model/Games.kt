package com.huntergaming.classicsolitaire.data.model

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.INTEGER
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "games")
data class Games @Inject constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "score", defaultValue = "0", typeAffinity = INTEGER) val highScore: Int
)