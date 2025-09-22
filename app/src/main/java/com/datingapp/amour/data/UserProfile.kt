package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserProfile entity for Room database.
 * Stores bio, prompts, interests, and preferences for profile setup.
 * Primary key is email, as each user has one profile.
 */
@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val email: String,    // each email corresponds to a single profile
    val bio: String,
    val gender: String,
    val prompt1: String,
    val prompt2: String,
    val prompt3: String,
    val interests: String,            // comma-separated interests
    val agePreference: String,
    val distancePreference: String
)
