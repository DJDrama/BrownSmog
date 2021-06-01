package com.dj.brownsmog.repository.main

import android.util.Log
import com.dj.brownsmog.data.model.Data
import com.dj.brownsmog.data.model.LocalCounter
import com.dj.brownsmog.data.model.SidoByul
import com.dj.brownsmog.data.model.SidoByulCounter
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.db.LocationDao
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.network.CovidApiService
import com.dj.brownsmog.network.IqAirRetrofitService
import com.dj.brownsmog.ui.closestCountryName
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository
@Inject
constructor(
    private val iqAirRetrofitService: IqAirRetrofitService,
    private val covidApiService: CovidApiService,
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

    suspend fun getBrownSmogFromMyLocation(latitude: Double, longitude: Double): Data? {
        try {
            val response =
                iqAirRetrofitService.getNearestCityData(latitude = latitude, longitude = longitude)
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    if (it.status == "success") {
                        return it.data
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getCovidInformation(latitude: Double, longitude: Double): SidoByulCounter? {
        try {
            val response = covidApiService.getSidoByulCovidData()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { covidResponse ->
                    if (covidResponse.resultCode == "0") {
                        return with(closestCountryName(latitude, longitude)){
                            covidResponse.list.find {
                                it.countryName == this
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}