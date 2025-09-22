package com.datingapp.amour.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The Room database for the Amour app.
 * Contains two entities: User and UserProfile.
 * Version 1: initial release.
 */
@Database(entities = [User::class, UserProfile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // DAO for user-related database operations
    abstract fun userDao(): UserDao

    // DAO for user profile-related operations
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Singleton pattern: returns a single instance of AppDatabase.
         * Ensures only one database instance exists across threads.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "amour_database" // database file name
                ).fallbackToDestructiveMigration() // safely recreate DB on version upgrade
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
