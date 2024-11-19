package com.example.appfutebol.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appfutebol.Model.Desempenho

@Dao
interface DesempenhoDao {

    @Query("SELECT * FROM desempenho")
    suspend fun buscarTodosDesempenhos(): List<Desempenho>

    @Query("SELECT * FROM desempenho WHERE id_desempenho = :id")
    suspend fun buscarDesempenhoPorId(id: Int): Desempenho

    @Insert
    suspend fun adicionarDesempenho(desempenho: Desempenho)

    @Update
    suspend fun editarDesempenho(desempenho: Desempenho)

    @Delete
    suspend fun excluirDesempenho(desempenho: Desempenho)

    @Query("DELETE FROM desempenho WHERE id_desempenho = :id")
    suspend fun excluirDesempenhoPorId(id: Int)
}