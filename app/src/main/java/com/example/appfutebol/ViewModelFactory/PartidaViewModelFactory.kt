package com.example.appfutebol.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfutebol.Dao.PartidaDao
import com.example.appfutebol.ViewModel.PartidaViewModel

class PartidaViewModelFactory (private val partidaDao: PartidaDao): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(PartidaViewModel::class.java)){
            return PartidaViewModel(partidaDao) as T
        }
        throw IllegalArgumentException("Classe PartidaViewModel desconhecida")
    }
}