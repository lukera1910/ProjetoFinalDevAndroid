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
    fun buscarJogadorPorId(id: Int): Jogador? {
        var jogador: Jogador? = null
        viewModelScope.launch{
            jogador = jogadorDao.buscarJogadorPorId(id)
        }

        return jogador
    }

    //Faz a Busca no banco do jogador com esse Nome, se não existir retorna null
    //Função Assíncrona
    fun buscarJogadorPorNome(nome: String): Jogador? {
        var jogador: Jogador? = null
        viewModelScope.launch{
            jogador = jogadorDao.buscarJogadorPorNome(nome)
        }

        return jogador
    }

    //Faz a Busca no banco do jogador com essa Posição, se não existir retorna null
    //Função Assíncrona
    fun buscarJogadorPorPosicao(posicao: String): Jogador? {
        var jogador: Jogador? = null
        viewModelScope.launch{
            jogador = jogadorDao.buscarJogadorPorPosicao(posicao)
        }

        return jogador
    }

    //Salva o Novo Jogador no Banco
    fun adicionarJogador(nome: String, posicao: String, numero: Int, pernaBoa: String, altura: Double,
                      idade: Int, nacionalidade: String): String{

        return try {
            if(nome.isBlank() || posicao.isBlank()|| pernaBoa.isBlank() || altura <= 0 || nacionalidade.isBlank() ){
                return "Preencha todos os campos!"
            }

            val novoJogador = Jogador(
                id = 0,
                nome,
                posicao,
                numero,
                pernaBoa,
                altura,
                idade,
                nacionalidade)

            viewModelScope.launch {
                jogadorDao.inserirJogador(novoJogador)
                carregarJogadores()
            }

            "Jogador salvo com sucesso!"
        } catch (e: Exception){
            "Erro ao adicionar Jogador: ${e.message}"
        }
    }

    //Atualiza um Jogador
    fun atualizarJogador(id: Int, nome: String, posicao: String, numero: Int, pernaBoa: String,
                                 altura: Double, idade: Int, nacionalidade: String): String {

        return try {
            if(nome.isBlank() || posicao.isBlank()|| pernaBoa.isBlank() || altura <= 0 || nacionalidade.isBlank() ) {
                return "Preencha todos os campos!"
            }

            viewModelScope.launch {
                val jogador = jogadorDao.buscarJogadorPorId(id)
                val jogadorAtualizado = jogador.copy(
                    nome = nome,
                    posicao = posicao,
                    numero = numero,
                    pernaBoa = pernaBoa,
                    altura = altura,
                    idade = idade,
                    nacionalidade = nacionalidade
                )
                jogadorDao.editarJogador(jogadorAtualizado)
                carregarJogadores()
            }

            "Jogador Atualizado!"
        }catch (e: Exception){
            "Erro ao atualizar Jogador: ${e.message}"
        }
    }

    //Exclui um jogador
    fun excluirJogador(jogador: Jogador): String{
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
