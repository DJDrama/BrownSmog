package com.dj.brownsmog.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    companion object {
        val DATABASE_NAME = "brownsmog_db"
    }
}