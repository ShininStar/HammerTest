package com.example.hammertest.data.repository

import com.example.hammertest.models.DisheModel

interface DisheRepository {
    suspend fun insertDishesModel(dishesModel: DisheModel)
    suspend fun getDishesModels()
    suspend fun getDishesModelsFromDb() : DisheModel
    suspend fun deleteData()
}