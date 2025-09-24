package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User entity for Room database and Firebase sync.
 * Represents the basic account info: name, email, phone, and password hash.
 * Primary key = email (unique key across offline and online).
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val name: String,
    val phone: String? = null,
    val passwordHash: String? = null,
    val age: Int? = null,
    val gender: String? = null,
    val orientation: String? = null,
    val location: String? = null,
    val profileImageUrl: String? = null,
    val imageUrls: String? = null, // Comma-separated URLs for additional images
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Convert User object into a Map<String, Any?> for Firebase updates.
     */
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "name" to name,
            "phone" to phone,
            "passwordHash" to passwordHash,
            "age" to age,
            "gender" to gender,
            "orientation" to orientation,
            "location" to location,
            "profileImageUrl" to profileImageUrl,
            "imageUrls" to imageUrls,
            "timestamp" to timestamp
        )
    }
}