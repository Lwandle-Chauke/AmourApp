package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Ignore
import com.google.firebase.database.Exclude

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val uid: String = "",
    val email: String = "",
    val name: String = "",

    // Profile information
    val age: Int? = null,
    val gender: String? = null,
    val orientation: String? = null,
    val location: String? = null,
    val profileImageUrl: String? = null,
    val imageUrls: String? = null,

    // Account metadata
    val createdAt: Long = System.currentTimeMillis(),
    val lastLogin: Long = System.currentTimeMillis(),
    val isProfileComplete: Boolean = false,
    val isEmailVerified: Boolean = false
) {
    @Ignore
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "email" to email,
            "name" to name,
            "age" to age,
            "gender" to gender,
            "orientation" to orientation,
            "location" to location,
            "profileImageUrl" to profileImageUrl,
            "imageUrls" to imageUrls,
            "createdAt" to createdAt,
            "lastLogin" to lastLogin,
            "isProfileComplete" to isProfileComplete,
            "isEmailVerified" to isEmailVerified
        )
    }

    companion object {
        fun fromMap(map: Map<String, Any>): User {
            return User(
                uid = map["uid"] as? String ?: "",
                email = map["email"] as? String ?: "",
                name = map["name"] as? String ?: "",
                age = (map["age"] as? Long)?.toInt(),
                gender = map["gender"] as? String,
                orientation = map["orientation"] as? String,
                location = map["location"] as? String,
                profileImageUrl = map["profileImageUrl"] as? String,
                imageUrls = map["imageUrls"] as? String,
                createdAt = map["createdAt"] as? Long ?: System.currentTimeMillis(),
                lastLogin = map["lastLogin"] as? Long ?: System.currentTimeMillis(),
                isProfileComplete = map["isProfileComplete"] as? Boolean ?: false,
                isEmailVerified = map["isEmailVerified"] as? Boolean ?: false
            )
        }
    }
}
