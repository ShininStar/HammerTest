package com.example.hammertest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hammertest.data.repository.DisheRepository
import com.example.hammertest.models.DisheModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisheViewModel @Inject constructor(
    private val repository: DisheRepository
) : ViewModel() {

    private val _liveDataDishe = MutableLiveData<DisheModel>()
    val liveDataDishe: LiveData<DisheModel> = _liveDataDishe

    suspend fun getBannerFromApi() {
        viewModelScope.launch {
            repository.deleteData()
            repository.getDishesModels()
            _liveDataDishe.postValue(repository.getDishesModelsFromDb())
        }
    }

    suspend fun getBannerFromDb() {
        viewModelScope.launch {
            _liveDataDishe.postValue(repository.getDishesModelsFromDb())
        }
    }
}