package com.example.appfutebol.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfutebol.Dao.DesempenhoDao
import com.example.appfutebol.ViewModel.DesempenhoViewModel

class DesempenhoViewModelFactory (private val desempenhoDao: DesempenhoDao): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(DesempenhoViewModel::class.java)){
            return DesempenhoViewModel(desempenhoDao) as T
        }
        throw IllegalArgumentException("Classe DesempenhoViewModel desconhecida")
    }
}