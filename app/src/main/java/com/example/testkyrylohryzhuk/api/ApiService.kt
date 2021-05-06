package com.example.testkyrylohryzhuk.api

import com.example.testkyrylohryzhuk.BuildConfig
import com.example.testkyrylohryzhuk.models.GoogleMapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(POINT)
    suspend fun getAddress(
        @Query("origin") from: String,
        @Query("destination") to: String,
        @Query("mode") mode: String = "driving",
        @Query("key") key: String = BuildConfig.API_KEY
    ): Response<GoogleMapResponse>

    companion object {
        private const val POINT = "maps/api/directions/json"
    }
}