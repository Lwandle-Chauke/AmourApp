package com.datingapp.amour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val email: String,    // primary key is email
    val bio: String,
    val gender: String,
    val prompt1: String,
    val prompt2: String,
    val prompt3: String,
    val interests: String,
    val agePreference: String,
    val distancePreference: String
)
