package com.dj.brownsmog.network

import com.dj.brownsmog.data.model.LocalCounter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidApiService {
    @GET("korea/")
    suspend fun getLocalCounter(
        @Query("serviceKey")
        serviceKey: String = SERVICE_KEY
    ): Response<LocalCounter>
}