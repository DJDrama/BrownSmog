package com.dj.brownsmog.repository.main

import android.util.Log
import com.dj.brownsmog.data.model.BrownSmogItem
import com.dj.brownsmog.data.model.SidoItem
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.db.LocationDao
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.network.NORMAL_CODE
import com.dj.brownsmog.network.NORMAL_MSG
import com.dj.brownsmog.network.RetrofitService
import com.dj.brownsmog.network.SERVICE_KEY
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class HomeRepository
@Inject
constructor(
    private val retrofitService: RetrofitService,
    private val locationDao: LocationDao,
    private val dataStoreImpl: DataStoreImpl,
) {

    fun getMyLocation(): Flow<LocationEntity?> = flow {
        val location = locationDao.getLocation(dataStoreImpl.userId.value)
        location?.let {
            emit(it)
        } ?: emit(null)
    }

    fun saveMyLocation(latLng: LatLng): Flow<Boolean> = flow {
        val latitude = latLng.latitude
        val longitude = latLng.longitude
        val res =
            locationDao.insertLocation(LocationEntity(id = dataStoreImpl.userId.value,
                latitude = latitude,
                longitude = longitude))
        if (res > 0)
            emit(true)
        else
            emit(false)
    }

    suspend fun getBrownSmogFromMyLocation(locality: String): List<BrownSmogItem>? {
        try {
            val response =
                retrofitService.getBrownSmogFromMyLocation(serviceKey = SERVICE_KEY,
                    sidoName = locality.substring(0, 2))
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    val airKoreaResponse = it.response
                    val airKoreaHeader = airKoreaResponse.header
                    if (airKoreaHeader.resultCode == NORMAL_CODE && airKoreaHeader.resultMsg == NORMAL_MSG) {
                        return airKoreaResponse.body.items
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}