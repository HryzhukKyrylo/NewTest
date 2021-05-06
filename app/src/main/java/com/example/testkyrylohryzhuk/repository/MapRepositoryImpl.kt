package com.example.testkyrylohryzhuk.repository

import com.example.testkyrylohryzhuk.api.ApiService
import com.example.testkyrylohryzhuk.models.GoogleMapResponse
import retrofit2.Response
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MapRepository {
    override suspend fun getPoints(from: String, to: String): Response<GoogleMapResponse> =
        apiService.getAddress(from, to)
}