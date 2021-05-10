package com.dj.brownsmog.repository.main

import android.util.Log
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.db.LocationDao
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.network.RetrofitService
import com.google.android.libraries.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
            locationDao.insertLocation(LocationEntity(id = dataStoreImpl.userId.value, latitude = latitude, longitude = longitude))
        if (res > 0)
            emit(true)
        else
            emit(false)
    }
}