package com.dj.brownsmog.db

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface LocationDao {

    @Insert
    suspend fun insertLocation(location: LocationEntity): Long

    @Query("SELECT * FROM locations LIMIT 1")
    suspend fun getLocation(): LocationEntity?

    @Update
    suspend fun updateLocation(location: LocationEntity): Long
}