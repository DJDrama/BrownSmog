package com.dj.brownsmog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int=1,
    @ColumnInfo(name="latitude")
    var latitude: Double,
    @ColumnInfo(name="longitude")
    var longitude: Double
)