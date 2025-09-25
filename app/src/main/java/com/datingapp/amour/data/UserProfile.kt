package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserProfile entity for profile details.
 * Links to User via uid.
 */
@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val uid: String,    // Use uid as primary key to match User
    val email: String,              // Keep email for reference
    val bio: String,
    val prompt1: String,
    val prompt2: String,
    val prompt3: String,
    val interests: String,            // comma-separated hobbies/interests
    val agePreference: String,        // e.g., "18-25"
    val distancePreference: String,   // e.g., "10km"
    val lookingFor: String           // Add this missing field
) {
    /**
     * Convert to Firebase-friendly map
     */
    fun toMap(): Map<String, Any?> = mapOf(
        "uid" to uid,
        "email" to email,
        "bio" to bio,
        "prompt1" to prompt1,
        "prompt2" to prompt2,
        "prompt3" to prompt3,
        "interests" to interests,
        "agePreference" to agePreference,
        "distancePreference" to distancePreference,
        "lookingFor" to lookingFor
    )

    companion object {
        fun fromMap(map: Map<String, Any>): UserProfile {
            return UserProfile(
                uid = map["uid"] as? String ?: "",
                email = map["email"] as? String ?: "",
                bio = map["bio"] as? String ?: "",
                prompt1 = map["prompt1"] as? String ?: "",
                prompt2 = map["prompt2"] as? String ?: "",
                prompt3 = map["prompt3"] as? String ?: "",
                interests = map["interests"] as? String ?: "",
                agePreference = map["agePreference"] as? String ?: "",
                distancePreference = map["distancePreference"] as? String ?: "",
                lookingFor = map["lookingFor"] as? String ?: ""
            )
        }
    }
}