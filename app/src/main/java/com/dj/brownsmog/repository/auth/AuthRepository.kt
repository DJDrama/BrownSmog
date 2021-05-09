package com.dj.brownsmog.repository.auth

import android.util.Log
import com.dj.brownsmog.datastore.DataStoreImpl
import com.dj.brownsmog.db.LocationDao
import com.dj.brownsmog.db.LocationEntity
import com.dj.brownsmog.db.UserDao
import com.dj.brownsmog.db.UserEntity
import com.dj.brownsmog.network.RetrofitService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository
@Inject
constructor(
    private val userDao: UserDao,
    private val dataStoreImpl: DataStoreImpl,
) {

    fun register(userId: String, password: String, nickName: String): Flow<Boolean> = flow {
        val userEntity = UserEntity(userId = userId, password = password, nickname = nickName)
        val response = userDao.register(userEntity)
        if (response > 0) {
            dataStoreImpl.setUserId(response.toInt())
            emit(true)
        } else {
            emit(false)
        }
    }

    fun login(userId: String, password: String): Flow<Boolean> = flow {
        val response = userDao.login(userId = userId, password = password)
        response?.let { id ->
            if (id > 0) {
                dataStoreImpl.setUserId(response.toInt())
                emit(true)
            } else {
                emit(false)
            }
        } ?: emit(false)
    }

    fun checkDuplicate(userId: String, nickName: String): Flow<Boolean> = flow{
        val response = userDao.checkDuplicates(userId = userId.trim(), nickname = nickName.trim())
        if(response>0){
            emit(true)
        }else{
            emit(false)
        }
    }
}