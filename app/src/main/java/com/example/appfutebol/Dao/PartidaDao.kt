package com.example.appfutebol.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appfutebol.Model.Partida

@Dao
interface PartidaDao {

    @Query("SELECT * FROM partida")
    suspend fun buscarTodasPartidas(): List<Partida>

    @Query("SELECT * FROM partida WHERE id_partida = :id")
    suspend fun buscarPartidaPorId(id: Int): Partida

    @Insert
    suspend fun adicionarPartida(partida: Partida)

    @Update
    suspend fun editarPartida(partida: Partida)

    @Delete
    suspend fun excluirPartida(partida: Partida)

    @Query("DELETE FROM partida WHERE id_partida = :id")
    suspend fun excluirPartidaPorId(id: Int)
}