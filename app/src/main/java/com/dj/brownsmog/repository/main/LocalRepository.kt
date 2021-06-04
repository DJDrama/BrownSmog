package com.dj.brownsmog.repository.main

import com.dj.brownsmog.data.model.SidoByulCounter
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.network.CovidApiService
import com.dj.brownsmog.network.NORMAL_CODE
import com.dj.brownsmog.network.NORMAL_MSG
import com.dj.brownsmog.network.OpenApiRetrofitService
import com.dj.brownsmog.network.SERVICE_KEY
import com.dj.brownsmog.ui.closestCountryName
import java.lang.Exception
import javax.inject.Inject

class LocalRepository
@Inject
constructor(
    private val covidApiService: CovidApiService,
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

    suspend fun getCovidInformation(): List<SidoByulCounter>? {
        try {
            val response = covidApiService.getSidoByulCovidData()
            val list = mutableListOf<SidoByulCounter>()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { covidResponse ->
                    if (covidResponse.resultCode == "0") {
                        list.add(covidResponse.korea)
                        list.add(covidResponse.seoul)
                        list.add(covidResponse.busan)
                        list.add(covidResponse.daegu)
                        list.add(covidResponse.incheon)
                        list.add(covidResponse.gwangju)
                        list.add(covidResponse.daejeon)
                        list.add(covidResponse.ulsan)
                        list.add(covidResponse.sejong)
                        list.add(covidResponse.gyeonggi)
                        list.add(covidResponse.gangwon)
                        list.add(covidResponse.chungnam)
                        list.add(covidResponse.chungbuk)
                        list.add(covidResponse.jeonnam)
                        list.add(covidResponse.jeonbuk)
                        list.add(covidResponse.gyeongnam)
                        list.add(covidResponse.gyeongbuk)
                        list.add(covidResponse.jeju)
                        list.add(covidResponse.quarantine)
                        return list
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}