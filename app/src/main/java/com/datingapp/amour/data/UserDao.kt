package com.datingapp.amour.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    suspend fun getUserByUid(uid: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET lastLogin = :timestamp WHERE uid = :uid")
    suspend fun updateLastLogin(uid: String, timestamp: Long = System.currentTimeMillis())

    @Query("DELETE FROM users WHERE uid = :uid")
    suspend fun deleteUser(uid: String)
}
