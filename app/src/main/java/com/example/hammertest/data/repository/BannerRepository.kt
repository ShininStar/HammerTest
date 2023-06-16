package com.example.hammertest.data.repository

import com.example.hammertest.models.BannerModel

interface BannerRepository {
    suspend fun insertBannerModel(bannerModel: BannerModel)
    suspend fun getBannerModels()
    suspend fun getBannerModelsFromDb() : BannerModel
    suspend fun deleteData()
}