package com.example.appfutebol.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appfutebol.Model.Jogador

class JogadorViewModel : ViewModel() {

    // MutableLiveData para armazenar a lista de jogadores
    private val _todosJogadores = MutableLiveData<List<Jogador>>()
    val todosJogadores: LiveData<List<Jogador>> get() = _todosJogadores

    // Função para adicionar jogadores à lista
    fun adicionarJogador(jogador: Jogador) {
        val currentList = _todosJogadores.value?.toMutableList() ?: mutableListOf()
        currentList.add(jogador)
        _todosJogadores.value = currentList
    }

    // Função para carregar jogadores iniciais
    fun carregarJogadores() {
        val jogadoresIniciais = listOf(
            Jogador(
                nome = "Cristiano Ronaldo", posicao = "Atacante", numero = 7,
                pernaBoa = "Direita", altura = 1.87, idade = 39, nacionalidade = "Português"
            ),
            Jogador(
                nome = "Lionel Messi", posicao = "Meio-campo", numero = 10,
                pernaBoa = "Esquerda", altura = 1.70, idade = 36, nacionalidade = "Argentino"
            )
        )
        _todosJogadores.value = jogadoresIniciais
    }
}
