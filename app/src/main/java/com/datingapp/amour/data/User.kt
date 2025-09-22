package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String? = null,        // optional for phone-only signup
    val phone: String? = null,        // optional for phone authentication
    val passwordHash: String? = null  // optional if signing up via phone
)
