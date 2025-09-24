package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserProfile entity for Room database and Firebase sync.
 * Stores bio, prompts, interests, and preferences for profile setup.
 * Primary key = email (unique link to User).
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
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "bio" to bio,
            "gender" to gender,
            "prompt1" to prompt1,
            "prompt2" to prompt2,
            "prompt3" to prompt3,
            "interests" to interests,
            "agePreference" to agePreference,
            "distancePreference" to distancePreference
        )
    }
}
