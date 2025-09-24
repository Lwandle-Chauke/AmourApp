package com.datingapp.amour.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO interface for User entity operations.
 * Provides database access functions for User table.
 */
@Dao
interface UserDao {

    // Insert or update user locally
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    // Get a user by email
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?


}
