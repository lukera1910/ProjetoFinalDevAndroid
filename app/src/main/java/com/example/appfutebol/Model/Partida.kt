package com.example.appfutebol.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partida")
data class Partida(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_partida")
    var id: Int = 0,

    var data: String,
    var adversario: String,
    var resultado: String
)