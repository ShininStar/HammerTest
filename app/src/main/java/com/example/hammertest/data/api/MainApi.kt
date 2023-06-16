package com.example.hammertest.data.api

import com.example.hammertest.models.BannerModel
import com.example.hammertest.models.DisheModel
import retrofit2.http.GET


/*интерфейс запроса на сервер, ссылки брал из одного тестового API, при реально запросе нужно знать
* документацию сервера и делать соответствующие запросы по ней*/
interface MainApi {

    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getBanners(): BannerModel

    @GET("c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes(): DisheModel
}