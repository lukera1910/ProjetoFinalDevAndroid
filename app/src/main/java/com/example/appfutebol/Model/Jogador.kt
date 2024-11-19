package com.example.appfutebol.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jogador")
data class Jogador(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_jogador")
    var id: Int = 0,

    var nome: String,
    var posicao: String,
    var numero: Int = 1,

    @ColumnInfo(name = "perna_boa")
    var pernaBoa: String,

    var altura: Double,
    var idade: Int = 16,
    var nacionalidade: String
){
    init {
        require(numero in 1..99) { "Um jogador não pode usar uma camisa do número 0 ou de um número maior que 99" }
        require(idade in 16..47) { "Por padrão é muito difícil ter um jogador com idade maior que 47 ou menor que 16 entre os profissionais" }
    }
}