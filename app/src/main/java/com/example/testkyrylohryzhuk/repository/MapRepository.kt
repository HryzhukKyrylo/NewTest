package com.example.testkyrylohryzhuk.repository

import com.example.testkyrylohryzhuk.models.GoogleMapResponse
import retrofit2.Response

interface MapRepository {
    suspend fun getPoints(from: String, to: String): Response<GoogleMapResponse>
}