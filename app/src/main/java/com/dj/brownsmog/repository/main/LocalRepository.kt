package com.dj.brownsmog.repository.main

import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.network.NORMAL_CODE
import com.dj.brownsmog.network.NORMAL_MSG
import com.dj.brownsmog.network.OpenApiRetrofitService
import com.dj.brownsmog.network.SERVICE_KEY
import java.lang.Exception
import javax.inject.Inject

class LocalRepository
@Inject
constructor(
    private val retrofitService: OpenApiRetrofitService,
) {

    suspend fun getSidoMeasuredData(sidoName: String): List<SidoItem>? {
        try {
            val response =
                retrofitService.getSidobyulBrownSmog(serviceKey = SERVICE_KEY, sidoName = sidoName)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val sidoResponse = it.response
                    val sidoHeader = sidoResponse.header
                    if (sidoHeader.resultCode == NORMAL_CODE && sidoHeader.resultMsg == NORMAL_MSG) {
                        return sidoResponse.body.items
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}