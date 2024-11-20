package com.example.appfutebol.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfutebol.Dao.PartidaDao
import com.example.appfutebol.Model.Jogador
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

    //Faz a Busca no banco da partida com esse Id, se não existir retorna null
    //Função Assíncrona
    suspend fun buscarPartidaPorId(id: Int): Partida? {
        return partidaDao.buscarPartidaPorId(id)
    }

    // Adiciona uma nova Partida
    fun adicionarPartida(data: String, adversario: String, resultado: String): String {
        return try {
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

            "Partida adicionada com sucesso!"
        } catch (e: Exception){
            "Erro ao adicionar partida: ${e.message}"
        }
    }

    // Atualiza uma Partida existente
    fun atualizarPartida(
        id: Int,
        data: String,
        adversario: String,
        resultado: String
    ): String {

        return try {
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

            "Partida atualizada com sucesso!"
        } catch (e: Exception){
            "Erro ao atualizar partida: ${e.message}"
        }
    }

    // Exclui uma Partida
    fun excluirPartida(partida: Partida): String {
        return try {
            viewModelScope.launch {
                partidaDao.excluirPartida(partida)
                carregarPartidas()
            }
            "Partida excluída com sucesso!"
        } catch (e: Exception){
            "Erro ao excluir partida: ${e.message}"
        }

    }
}
