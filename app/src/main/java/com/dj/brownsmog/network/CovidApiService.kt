package com.dj.brownsmog.network

import com.dj.brownsmog.data.model.LocalCounter
import com.dj.brownsmog.data.model.SidoByul
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidApiService {
    @GET("korea/")
    suspend fun getLocalCounter(
        @Query("serviceKey")
        serviceKey: String = COVID_SERVICE_KEY
    ): Response<LocalCounter>


    @GET("korea/country/new/")
    suspend fun getSidoByulCovidData(
        @Query("serviceKey")
        serviceKey: String = COVID_SERVICE_KEY
    ): Response<SidoByul>
}