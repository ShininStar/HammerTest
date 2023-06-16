package com.example.hammertest.models

import androidx.room.Entity
import androidx.room.PrimaryKey

//модель блюд приходит с API
@Entity(tableName = "dishes_table")
data class DisheModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val dishes: List<Dishe>
)