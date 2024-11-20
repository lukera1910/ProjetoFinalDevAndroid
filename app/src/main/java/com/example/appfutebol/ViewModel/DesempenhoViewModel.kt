package com.example.appfutebol.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfutebol.Dao.DesempenhoDao
import com.example.appfutebol.Model.Desempenho
import kotlinx.coroutines.launch

class DesempenhoViewModel(private val desempenhoDao: DesempenhoDao) : ViewModel() {

    var listaDesempenhos = mutableStateOf(listOf<Desempenho>())
        private set

    init {
        carregarDesempenhos()
    }

    // Carrega todos os desempenhos do banco de dados
    private fun carregarDesempenhos() {
        viewModelScope.launch {
            listaDesempenhos.value = desempenhoDao.buscarTodosDesempenhos()
        }
    }

    // Adiciona um novo desempenho ao banco de dados
    fun adicionarDesempenho(
        idJogador: Int,
        idPartida: Int,
        gols: Int,
        assists: Int,
        numCartoesAmarelos: Int,
        numCartoesVermelhos: Int,
        nota: Double,
        minutosJogados: Int
    ): String {
        if (nota !in 0.0..10.0) {
            return "A nota deve estar entre 0 e 10."
        }

        val novoDesempenho = Desempenho(
            idJogador = idJogador,
            idPartida = idPartida,
            gols = gols,
            assists = assists,
            numCartoesAmarelos = numCartoesAmarelos,
            numCartoesVermelhos = numCartoesVermelhos,
            nota = nota,
            minutosJogados = minutosJogados
        )

        viewModelScope.launch {
            desempenhoDao.adicionarDesempenho(novoDesempenho)
            carregarDesempenhos()
        }

        return "Desempenho adicionado com sucesso!"
    }

    // Atualiza os dados de um desempenho existente
    fun atualizarDesempenho(
        id: Int,
        idJogador: Int,
        idPartida: Int,
        gols: Int,
        assists: Int,
        numCartoesAmarelos: Int,
        numCartoesVermelhos: Int,
        nota: Double,
        minutosJogados: Int
    ): String {
        viewModelScope.launch {
            val desempenhoAtual = desempenhoDao.buscarDesempenhoPorId(id)
                ?: return@launch

            val desempenhoAtualizado = desempenhoAtual.copy(
                idJogador = idJogador,
                idPartida = idPartida,
                gols = gols,
                assists = assists,
                numCartoesAmarelos = numCartoesAmarelos,
                numCartoesVermelhos = numCartoesVermelhos,
                nota = nota,
                minutosJogados = minutosJogados
            )

            desempenhoDao.editarDesempenho(desempenhoAtualizado)
            carregarDesempenhos()
        }

        return "Desempenho atualizado com sucesso!"
    }

    // Exclui um desempenho
    fun excluirDesempenho(desempenho: Desempenho): String {
        viewModelScope.launch {
            desempenhoDao.excluirDesempenho(desempenho)
            carregarDesempenhos()
        }

        return "Desempenho excluído com sucesso!"
    }

    // Busca desempenho por ID
    suspend fun buscarDesempenhoPorId(id: Int): Desempenho? {
        return desempenhoDao.buscarDesempenhoPorId(id)
    }
}
