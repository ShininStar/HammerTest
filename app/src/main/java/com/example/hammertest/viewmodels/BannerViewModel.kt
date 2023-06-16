package com.example.hammertest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hammertest.data.repository.BannerRepository
import com.example.hammertest.models.BannerModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(
    private val repository: BannerRepository
) : ViewModel() {

    private val _liveDataBanner = MutableLiveData<BannerModel>()
    val liveDataBanner: LiveData<BannerModel> = _liveDataBanner

    suspend fun getBannerFromApi() {
        viewModelScope.launch {
            repository.deleteData()
            repository.getBannerModels()
            _liveDataBanner.postValue(repository.getBannerModelsFromDb())
        }
    }

    suspend fun getBannerFromDb() {
        viewModelScope.launch {
            _liveDataBanner.postValue(repository.getBannerModelsFromDb())
        }
    }
}