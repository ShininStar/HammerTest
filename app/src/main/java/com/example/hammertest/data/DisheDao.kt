package com.example.hammertest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hammertest.models.DisheModel

//дао для блюд
@Dao
interface DisheDao {
    //добавляем модель в базу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishesModel(dishesModel: DisheModel)

    //берем модель из базы
    @Query("SELECT * FROM dishes_table")
    suspend fun getDishesModels() : DisheModel

    //удаляем данные из базы
    @Query("DELETE FROM dishes_table")
    suspend fun deleteData()
}