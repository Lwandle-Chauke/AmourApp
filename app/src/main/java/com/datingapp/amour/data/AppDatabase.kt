package com.datingapp.amour.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The Room database for the Amour app.
 * Contains two entities: User and UserProfile.
 * Version 1: initial release.
 *
 * NOTE: We are NOT disabling exportSchema.
 * This allows Room to export schema history JSON files
 * into the directory we configure in build.gradle.kts.
 */
@Database(
    entities = [User::class, UserProfile::class],
    version = 1,
    exportSchema = true // 👈 explicitly enabled for schema history tracking
)
abstract class AppDatabase : RoomDatabase() {

    // DAO for user-related database operations
    abstract fun userDao(): UserDao

    // DAO for user profile-related operations
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Singleton pattern: ensures a single database instance across threads.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "amour_database" // Database filename (amour_database.db)
                )
                    // During development, recreate DB if schema changes.
                    // In production, you’ll want proper migrations instead.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
