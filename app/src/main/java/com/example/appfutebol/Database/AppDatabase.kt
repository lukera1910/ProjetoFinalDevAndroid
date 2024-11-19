package com.example.appfutebol.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appfutebol.Dao.DesempenhoDao
import com.example.appfutebol.Dao.JogadorDao
import com.example.appfutebol.Dao.PartidaDao
import com.example.appfutebol.Model.Desempenho
import com.example.appfutebol.Model.Jogador
import com.example.appfutebol.Model.Partida

@Database(
    entities = [Jogador::class, Partida::class, Desempenho::class],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jogadorDao(): JogadorDao
    abstract fun partidaDao(): PartidaDao
    abstract fun desempenhoDao(): DesempenhoDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}