package com.example.appfutebol.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfutebol.Dao.PartidaDao
import com.example.appfutebol.Model.Partida
import kotlinx.coroutines.launch

class PartidaViewModel(private val partidaDao: PartidaDao) : ViewModel() {

    var listaPartidas = mutableStateOf(listOf<Partida>())
        private set

    init {
        carregarPartidas()
    }

    // Carrega todas as Partidas do Banco
    private fun carregarPartidas() {
        viewModelScope.launch {
            listaPartidas.value = partidaDao.buscarTodasPartidas()
        }
    }

    // Adiciona uma nova Partida
    fun adicionarPartida(data: String, adversario: String, resultado: String): String {
        if (data.isBlank() || adversario.isBlank() || resultado.isBlank()) {
            return "Preencha todos os campos!"
        }

        val novaPartida = Partida(
            id = 0,
            data = data,
            adversario = adversario,
            resultado = resultado
        )

        viewModelScope.launch {
            partidaDao.adicionarPartida(novaPartida)
            carregarPartidas()
        }

        return "Partida adicionada com sucesso!"
    }

    // Atualiza uma Partida existente
    fun atualizarPartida(
        id: Int,
        data: String,
        adversario: String,
        resultado: String
    ): String {
        if (data.isBlank() || adversario.isBlank() || resultado.isBlank()) {
            return "Preencha todos os campos!"
        }

        viewModelScope.launch {
            val partidaExistente = partidaDao.buscarPartidaPorId(id)
            if (partidaExistente != null) {
                val partidaAtualizada = partidaExistente.copy(
                    data = data,
                    adversario = adversario,
                    resultado = resultado
                )

                partidaDao.editarPartida(partidaAtualizada)
                carregarPartidas()
            }
        }

        return "Partida atualizada com sucesso!"
    }

    // Exclui uma Partida
    fun excluirPartida(partida: Partida): String {
        viewModelScope.launch {
            partidaDao.excluirPartida(partida)
            carregarPartidas()
        }
        return "Partida excluída com sucesso!"
    }
}
