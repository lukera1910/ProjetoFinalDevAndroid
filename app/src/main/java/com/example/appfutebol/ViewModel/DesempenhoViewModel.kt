package com.example.appfutebol.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfutebol.Dao.DesempenhoDao
import com.example.appfutebol.Model.Desempenho
import com.example.appfutebol.Model.Partida
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

    // Busca desempenho por ID
    suspend fun buscarDesempenhoPorId(id: Int): Desempenho? {
        return desempenhoDao.buscarDesempenhoPorId(id)
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

        return try{
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

            "Desempenho adicionado com sucesso!"
        } catch (e: Exception){
            "Erro ao adicionar Desempenho: ${e.message}"
        }
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
        return try {
            viewModelScope.launch {
                val desempenhoAtual = desempenhoDao.buscarDesempenhoPorId(id)

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

            "Desempenho atualizado com sucesso!"
        }catch (e: Exception){
            "Erro ao atualizar desempenho: ${e.message}"
        }
    }

    // Exclui um desempenho
    fun excluirDesempenho(desempenho: Desempenho): String {
        return try {
            viewModelScope.launch {
                desempenhoDao.excluirDesempenho(desempenho)
                carregarDesempenhos()
            }

            "Desempenho excluído com sucesso!"
        } catch (e: Exception){
            "Erro ao excluir desempenho: ${e.message}"
        }
    }

}
