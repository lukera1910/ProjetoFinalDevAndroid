package com.example.appfutebol.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "desempenho",
    foreignKeys = [
        ForeignKey(
            entity = Jogador::class,
            parentColumns = ["id_jogador"],
            childColumns = ["id_jogador"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Partida::class,
            parentColumns = ["id_partida"],
            childColumns = ["id_partida"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["id_jogador"]),
        Index(value = ["id_partida"])
    ]
)

data class Desempenho(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_desempenho")
    var id: Int = 0,

    @ColumnInfo(name = "id_jogador")
    var idJogador: Int,

    @ColumnInfo(name = "id_partida")
    var idPartida: Int,

    var gols: Int = 0,
    var assists: Int = 0,

    @ColumnInfo(name = "num_cartoes_amarelos")
    var numCartoesAmarelos: Int = 0,

    @ColumnInfo(name = "num_cartoes_vermelhos")
    var numCartoesVermelhos: Int = 0,

    var nota: Double = 0.0,

    @ColumnInfo(name = "minutos_jogados")
    var minutosJogados: Int = 0
){
    init {
        require(gols >= 0) { "Número de gols não pode ser negativo" }
        require(assists >= 0) { "Número de assistências não pode ser negativo" }
        require(numCartoesAmarelos >= 0) { "Número de cartões amarelos não pode ser negativo" }
        require(numCartoesVermelhos >= 0) { "Número de cartões vermelhos não pode ser negativo" }
        require(nota in 0.0 .. 10.0) { "Nota deve estar entre 0 e 10" }
        require(minutosJogados >= 0) { "Número de minutos Jogados não pode ser negativo" }
    }
}