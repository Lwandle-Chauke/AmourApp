package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * User entity for Room database and offline storage.
 * Represents the basic account info: name, email, phone, and password hash.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generated primary key
    val name: String,
    val email: String? = null,        // optional for phone-only signup
    val phone: String? = null,        // optional for phone authentication
    val passwordHash: String? = null  // optional if signing up via phone
)
