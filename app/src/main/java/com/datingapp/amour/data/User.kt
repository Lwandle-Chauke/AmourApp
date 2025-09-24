package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user account.
 * Stored in Room and can be synced to Firebase.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,       // unique identifier
    var name: String? = null,
    var passwordHash: String? = null,
    var phone: String? = null,
    var age: Int? = null,
    var gender: String? = null,
    var orientation: String? = null,
    var location: String? = null,
    var profileImageUrl: String? = null,
    var imageUrls: String? = null        // comma-separated additional images
) {
    /**
     * Convert User object to a Map for Firebase storage.
     */
    fun toMap(): Map<String, Any?> = mapOf(
        "email" to email,
        "name" to name,
        "passwordHash" to passwordHash,
        "phone" to phone,
        "age" to age,
        "gender" to gender,
        "orientation" to orientation,
        "location" to location,
        "profileImageUrl" to profileImageUrl,
        "imageUrls" to imageUrls
    )

    companion object {
        /**
         * Converts a Firebase Map back into a User object.
         */
        fun fromMap(map: Map<String, Any?>): User {
            return User(
                email = map["email"] as? String ?: "",
                name = map["name"] as? String,
                passwordHash = map["passwordHash"] as? String,
                phone = map["phone"] as? String,
                age = (map["age"] as? Long)?.toInt(),
                gender = map["gender"] as? String,
                orientation = map["orientation"] as? String,
                location = map["location"] as? String,
                profileImageUrl = map["profileImageUrl"] as? String,
                imageUrls = map["imageUrls"] as? String
            )
        }
    }
}
