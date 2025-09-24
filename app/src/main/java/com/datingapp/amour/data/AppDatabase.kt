package com.datingapp.amour.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The Room database for the Amour app.
 *
 * Contains two entities:
 * - User: core account info (email, name, age, gender, orientation, profile images)
 * - UserProfile: profile details (bio, prompts, interests, preferences)
 *
 * Version 1: initial release
 */
@Database(
    entities = [User::class, UserProfile::class],
    version = 2, // increment version whenever schema changes
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    // DAO for user-related database operations
    abstract fun userDao(): UserDao

    // DAO for user profile operations
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Singleton pattern: ensures one database instance across threads.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "amour_database" // Filename: amour_database.db
                )
                    .fallbackToDestructiveMigration() // Rebuilds DB if schema changes (dev only)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
