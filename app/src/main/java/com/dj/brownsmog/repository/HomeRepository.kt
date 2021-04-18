package com.dj.brownsmog.repository

import com.dj.brownsmog.db.LocationDao
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository
@Inject
constructor(
    private val retrofitService: RetrofitService,
    private val locationDao: LocationDao
) {

    fun getMyLocation(): Flow<LocationEntity?> = flow{
        val location = locationDao.getLocation()
        location?.let{
            emit(it)
        } ?: emit(null)
    }
}