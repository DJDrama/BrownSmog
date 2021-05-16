package com.dj.brownsmog.network

import com.dj.brownsmog.data.response.IqAirResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IqAirRetrofitService {

    @GET("nearest_city")
    suspend fun getNearestCityData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("key") key: String = IQ_AIR_VISUAL_KEY,
    ): Response<IqAirResponse>
}
