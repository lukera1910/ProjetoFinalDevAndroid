package com.example.appfutebol.ViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfutebol.Dao.JogadorDao
import com.example.appfutebol.Model.Jogador
import kotlinx.coroutines.launch

class JogadorViewModel(private val jogadorDao: JogadorDao) : ViewModel() {

    var listaJogadores = mutableStateOf(listOf<Jogador>())
        private set

    init {
        carregarJogadores()
    }

    //Carrega todos os Jogadores do Banco
    private fun carregarJogadores(){
        viewModelScope.launch {
            listaJogadores.value = jogadorDao.buscarTodosJogadores()
        }
    }

    //Faz a Busca no banco do jogador com esse Id, se não existir retorna null
    //Função Assíncrona
    suspend fun buscarJogadorPorId(id: Int): Jogador? {
        return jogadorDao.buscarJogadorPorId(id)
    }

    //Faz a Busca no banco do jogador com esse Nome, se não existir retorna null
    //Função Assíncrona
    suspend fun buscarJogadorPorNome(nome: String): Jogador? {
        return jogadorDao.buscarJogadorPorNome(nome)
    }

    //Faz a Busca no banco do jogador com essa Posição, se não existir retorna null
    //Função Assíncrona
    suspend fun buscarJogadorPorPosicao(posicao: String): Jogador? {
        return jogadorDao.buscarJogadorPorPosicao(posicao)
    }

    //Salva o Novo Jogador no Banco
    suspend fun salvarJogador(nome: String, posicao: String, numero: Int, pernaBoa: String, altura: Double,
                      idade: Int, nacionalidade: String): String{
        if(nome.isBlank() || posicao.isBlank()|| pernaBoa.isBlank() || altura <= 0 || nacionalidade.isBlank() ){
            return "Preencha todos os campos!"
        }

        val jogador = Jogador(id = 0, nome, posicao, numero, pernaBoa, altura, idade, nacionalidade)

        viewModelScope.launch {
            jogadorDao.inserirJogador(jogador)
            carregarJogadores()
        }

        return "Jogador salvo com sucesso!"
    }

    //Atualiza um Jogador
    suspend fun atualizarJogador(id: Int, nome: String, posicao: String, numero: Int, pernaBoa: String,
                                 altura: Double, idade: Int, nacionalidade: String): String {
        if(nome.isBlank() || posicao.isBlank()|| pernaBoa.isBlank() || altura <= 0 || nacionalidade.isBlank() ) {
            return "Preencha todos os campos!"
        }


        val jogador = buscarJogadorPorId(id) ?: return "Jogador com ID $id não encontrado!"

        val jogadorAtualizado = jogador.copy(
            nome = nome,
            posicao = posicao,
            numero = numero,
            pernaBoa = pernaBoa,
            altura = altura,
            idade = idade,
            nacionalidade = nacionalidade
        )

        viewModelScope.launch {
            jogadorDao.editarJogador(jogadorAtualizado)
            carregarJogadores()
        }

        return "Jogador Atualizado!"
    }

    //Exclui um jogador
    suspend fun excluirJogador(jogador: Jogador): String{
        return try{
            viewModelScope.launch {
                jogadorDao.excluirJogador(jogador)
                carregarJogadores()
            }

            "Jogador excluído com sucesso!"
        } catch (e: Exception){
            "Erro ao excluir jogador: ${e.message}"
        }
    }


}
