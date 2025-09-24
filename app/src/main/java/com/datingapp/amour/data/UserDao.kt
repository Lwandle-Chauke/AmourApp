package com.datingapp.amour.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO interface for User entity operations.
 */
@Dao
interface UserDao {

    /**
     * Insert or update a user in local Room database.
     * If a conflict (same email) occurs, replace the existing record.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    /**
     * Retrieve a user by email from the local database.
     */
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}
