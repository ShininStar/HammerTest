package com.example.hammertest.data.repository

import com.example.hammertest.data.DisheDao
import com.example.hammertest.data.api.MainApi
import com.example.hammertest.models.DisheModel
import javax.inject.Inject

//реализация интерфейса для блюд
class DisheRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    private val disheDao: DisheDao
) : DisheRepository {
    //добавление моедли в бд
    override suspend fun insertDishesModel(dishesModel: DisheModel) {
        disheDao.insertDishesModel(dishesModel)
    }

    //делаем запрос на сервер и добавляем модель в дб
    override suspend fun getDishesModels() {
        disheDao.insertDishesModel(mainApi.getDishes())
    }

    //берем модель из бд
    override suspend fun getDishesModelsFromDb(): DisheModel {
        return disheDao.getDishesModels()
    }

    //удаляем данные из бд
    override suspend fun deleteData() {
        disheDao.deleteData()
    }
}