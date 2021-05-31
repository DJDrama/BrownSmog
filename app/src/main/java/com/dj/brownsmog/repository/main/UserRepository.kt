package com.dj.brownsmog.repository.main

import android.util.Log
import com.dj.brownsmog.data.model.LocalCounter
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.db.UserDao
import com.dj.brownsmog.db.UserEntity
import com.dj.brownsmog.network.CovidApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository
@Inject
constructor(
    private val userDao: UserDao,
    private val covidApiService: CovidApiService,
    private val dataStoreImpl: DataStoreImpl,
) {

    fun getMyInformation(): Flow<UserEntity> = flow {
        val response = userDao.getUserInfo(id = dataStoreImpl.userId.value)
        emit(response)
    }

    fun exitMember(): Flow<Boolean> = flow {
        val response = userDao.deleteMember(id = dataStoreImpl.userId.value)
        if (response > 0)
            emit(true)
        else
            emit(false)
    }

    suspend fun getLocalCounter(): Flow<LocalCounter?> = flow {
        try {
            val response = covidApiService.getLocalCounter()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let { covidResponse ->
                    if (covidResponse.resultCode == "0") {
                        emit(covidResponse)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        emit(null)
    }
}