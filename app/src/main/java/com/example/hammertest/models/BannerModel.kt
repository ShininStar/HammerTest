package com.example.hammertest.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//модель баннеров приходит с API
@Entity(tableName = "banner_table")
data class BannerModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("сategories")
    val categories: List<Category>
)