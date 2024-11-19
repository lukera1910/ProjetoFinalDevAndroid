package com.example.appfutebol.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appfutebol.Model.Jogador

@Dao
interface JogadorDao {

    @Query("SELECT * FROM jogador")
    suspend fun buscarTodosJogadores(): List<Jogador>

    @Query("SELECT * FROM jogador WHERE id_jogador = :id")
    suspend fun buscarJogadorPorId(id: Int): Jogador

    @Query("SELECT * FROM jogador WHERE nome = :nomeJogador")
    suspend fun buscarJogadorPorNome(nomeJogador: String): Jogador

    @Query("SELECT * FROM jogador WHERE posicao = :posicao")
    suspend fun buscarJogadorPorPosicao(posicao: String): Jogador

    @Insert
    suspend fun inserirJogador(jogador: Jogador)

    @Update
    suspend fun editarJogador(jogador: Jogador)

    @Delete
    suspend fun excluirJogador(jogador: Jogador)

    @Query("DELETE FROM jogador WHERE id_jogador = :id")
    suspend fun excluirJogadorPorId(id: Int)
}