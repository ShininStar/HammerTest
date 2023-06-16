package com.example.hammertest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hammertest.models.BannerModel

//дао для баннеров
@Dao
interface BannerDao {
    //добавляем моедль в базу
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBannerModel(categoryModel: BannerModel)

    //достаем модель из базы
    @Query("SELECT * FROM banner_table")
    suspend fun getBannerModels() : BannerModel

    //удаляем все из базы
    @Query("DELETE FROM banner_table")
    suspend fun deleteData()
}