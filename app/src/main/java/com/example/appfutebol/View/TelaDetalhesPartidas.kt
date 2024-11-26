package com.example.appfutebol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.appfutebol.Database.AppDatabase
import com.example.appfutebol.screens.TelaDetalhesPartida
import com.example.appfutebol.ui.theme.AppFutebolTheme
import kotlinx.coroutines.launch

class DetalhesPartidaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtendo o ID da partida
        val partidaId = intent.getIntExtra("partidaId", 0)

        // Inicializando o banco de dados
        val db = AppDatabase.getDatabase(applicationContext)
        val partidaDao = db.partidaDao()
        val desempenhoDao = db.desempenhoDao()

        setContent {
            AppFutebolTheme {
                val partida = partidaDao.getPartidaById(partidaId)
                val desempenhos = desempenhoDao.getDesempenhoPorPartidaFlow(partidaId).collectAsState(initial = emptyList()).value

                TelaDetalhesPartida(
                    partida = partida,
                    desempenhos = desempenhos,
                    onAdicionarDesempenho = { desempenho ->
                        lifecycleScope.launch {
                            desempenhoDao.adicionarDesempenho(desempenho)
                        }
                    }
                )
            }
        }
    }
}
