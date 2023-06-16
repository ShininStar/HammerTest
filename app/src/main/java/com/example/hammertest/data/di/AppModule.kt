package com.example.hammertest.data.di

import android.app.Application
import androidx.room.Room
import com.example.hammertest.data.MainDb
import com.example.hammertest.data.api.MainApi
import com.example.hammertest.data.repository.BannerRepository
import com.example.hammertest.data.repository.BannerRepositoryImpl
import com.example.hammertest.data.repository.DisheRepository
import com.example.hammertest.data.repository.DisheRepositoryImpl
import com.example.hammertest.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//DI для получения нужны объектов БД, API и репозиториев
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "data.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMainApi(): MainApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofit.create(MainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: MainApi, db: MainDb): BannerRepository {
        return BannerRepositoryImpl(api, db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideDishesRepository(api: MainApi, db: MainDb): DisheRepository {
        return DisheRepositoryImpl(api, db.dishesDao)
    }
}