package com.example.hammertest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hammertest.models.BannerModel
import com.example.hammertest.models.DisheModel

//наша база данных с двумя сущностями
@Database(
    entities = [BannerModel::class, DisheModel::class],
    version = 1
)
@TypeConverters(MyTypeConverter::class)
abstract class MainDb : RoomDatabase() {
    abstract val categoryDao: BannerDao
    abstract val dishesDao: DisheDao
}