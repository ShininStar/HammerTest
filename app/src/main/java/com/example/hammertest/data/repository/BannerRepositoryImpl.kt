package com.example.hammertest.data.repository

import com.example.hammertest.data.BannerDao
import com.example.hammertest.data.api.MainApi
import com.example.hammertest.models.BannerModel
import javax.inject.Inject

//реализация интерфейса
class BannerRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val bannerDao: BannerDao
) : BannerRepository {
    //добавление модели в базу
    override suspend fun insertBannerModel(bannerModel: BannerModel) {
        bannerDao.insertBannerModel(bannerModel)
    }
    //делаем запрос на сервер и сразу добавляем его в базу
    override suspend fun getBannerModels() {
        return bannerDao.insertBannerModel(mainApi.getBanners())
    }
    //достаем моедль из базы
    override suspend fun getBannerModelsFromDb(): BannerModel {
        return bannerDao.getBannerModels()
    }
    //очищаем базу
    override suspend fun deleteData() {
        bannerDao.deleteData()
    }
}