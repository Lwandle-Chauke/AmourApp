package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserProfile entity for profile details.
 * Links to User via email.
 */
@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val email: String,    // same as user's email
    val bio: String,
    val gender: String,
    val prompt1: String,
    val prompt2: String,
    val prompt3: String,
    val interests: String,            // comma-separated hobbies/interests
    val agePreference: String,        // e.g., "Friends,Short-term"
    val distancePreference: String    // e.g., education or location preference
) {
    /**
     * Convert to Firebase-friendly map
     */
    fun toMap(): Map<String, Any?> = mapOf(
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
