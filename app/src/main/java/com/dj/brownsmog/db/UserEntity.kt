package com.dj.brownsmog.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    @ColumnInfo(name="user_id")
    var userId: String,
    @ColumnInfo(name="password")
    var password: String,
    @ColumnInfo(name="nick_name")
    var nickname: String
)
