package com.dj.brownsmog.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun register(user: UserEntity): Long

    @Query("SELECT id FROM users WHERE user_id = :userId AND password = :password")
    suspend fun login(userId: String, password: String): Long?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserInfo(id: Int): UserEntity

    @Query("SELECT COUNT(*) FROM users WHERE user_id = :userId OR nick_name = :nickname")
    suspend fun checkDuplicates(userId: String, nickname: String): Int

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteMember(id: Int): Int
}