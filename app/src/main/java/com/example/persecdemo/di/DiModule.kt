package com.example.persecdemo.di

import com.example.persecdemo.data.NasaApiService
import com.example.persecdemo.data.datasource.NasaDatasource
import com.example.persecdemo.data.repo.NasaRepoImpl
import com.example.persecdemo.usecase.NasaRepo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        val BASE_URL = "https://api.nasa.gov/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NasaApiService =
        retrofit.create(NasaApiService::class.java)

    @Provides
    fun provideNasaRepo(
        nasaDatasource: NasaDatasource
    ): NasaRepo = NasaRepoImpl(nasaDatasource)
}