package com.example.testkyrylohryzhuk.di

import com.example.testkyrylohryzhuk.BuildConfig
import com.example.testkyrylohryzhuk.api.ApiService
import com.example.testkyrylohryzhuk.repository.MapRepository
import com.example.testkyrylohryzhuk.repository.MapRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().
            client(okHttpClient).
            baseUrl(BuildConfig.BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            build()

    @Singleton
    @Provides
     fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(
        ApiService::class.java)

    @Provides
    @Singleton
    fun provideMapRepository(apiService: ApiService): MapRepository = MapRepositoryImpl(apiService)
}