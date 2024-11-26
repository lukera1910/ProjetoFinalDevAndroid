package com.example.appfutebol.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfutebol.Dao.JogadorDao
import com.example.appfutebol.ViewModel.JogadorViewModel

class JogadorViewModelFactory (private val jogadorDao: JogadorDao): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(JogadorViewModel::class.java)){
            return JogadorViewModel(jogadorDao) as T
        }
        throw IllegalArgumentException("Classe JogadorViewModel desconhecida")
    }
}