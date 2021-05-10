package com.dj.brownsmog.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity): Long

    @Query("SELECT * FROM locations WHERE id=:userId LIMIT 1")
    suspend fun getLocation(userId: Int): LocationEntity?

    @Update
    suspend fun updateLocation(location: LocationEntity): Int
}