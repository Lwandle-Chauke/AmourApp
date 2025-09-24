package com.datingapp.amour.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO interface for UserProfile entity operations.
 */
@Dao
interface UserProfileDao {

    /**
     * Insert or update profile.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: UserProfile)

    /**
     * Retrieve profile by email.
     */
    @Query("SELECT * FROM user_profiles WHERE email = :email LIMIT 1")
    suspend fun getProfile(email: String): UserProfile?
}
