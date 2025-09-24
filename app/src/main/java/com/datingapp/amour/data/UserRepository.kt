package com.datingapp.amour.data

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Repository pattern implementation for data management
 * Handles synchronization between local Room database and Firebase Realtime Database
 * Provides a clean API for data operations throughout the app
 */
class UserRepository private constructor(context: Context) {

    // Local database instance (Room)
    private val db: AppDatabase = AppDatabase.getDatabase(context)

    // Firebase Realtime Database reference
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        /**
         * Singleton pattern: ensures single instance across the application
         * @param context Application context
         * @return UserRepository instance
         */
        fun getInstance(context: Context): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(context).also { instance = it }
            }
    }

    /** ------------------- USER ENTITY OPERATIONS ------------------- **/

    /**
     * Save user to both local (Room) and remote (Firebase) databases
     * @param user User object to save
     */
    suspend fun saveUser(user: User) {
        // Save to local database on background thread
        withContext(Dispatchers.IO) {
            db.userDao().insert(user)
        }
        // Save to Firebase
        saveUserToFirebase(user)
    }

    /**
     * Retrieve user from local Room database by email
     * @param email User's email address
     * @return User object or null if not found
     */
    suspend fun getUserByEmail(email: String): User? =
        withContext(Dispatchers.IO) {
            db.userDao().getUserByEmail(email)
        }

    /**
     * Save user to Firebase Realtime Database
     * @param user User object to save
     */
    private suspend fun saveUserToFirebase(user: User) {
        val firebaseKey = user.email.replace(".", "_") // Firebase key format
        firebaseDB.child("users").child(firebaseKey).setValue(user.toMap()).await()
    }

    /**
     * Retrieve user from Firebase by email
     * @param email User's email address
     * @return User object or null if not found
     */
    private suspend fun getUserFromFirebase(email: String): User? {
        val firebaseKey = email.replace(".", "_")
        val snapshot = firebaseDB.child("users").child(firebaseKey).get().await()

        return if (snapshot.exists()) {
            User.fromMap(snapshot.value as Map<String, Any?>)
        } else {
            null
        }
    }

    /**
     * Synchronize user data from Firebase to local database
     * @param email User's email address
     */
    suspend fun syncUserFromFirebase(email: String) {
        val userFromFirebase = getUserFromFirebase(email)
        userFromFirebase?.let {
            // Update local database with Firebase data
            withContext(Dispatchers.IO) {
                db.userDao().insert(it)
            }
        }
    }

    /** ------------------- USER PROFILE OPERATIONS ------------------- **/

    /**
     * Save user profile to both local and remote databases
     * @param profile UserProfile object to save
     */
    suspend fun saveUserProfile(profile: UserProfile) {
        // Save to local database
        withContext(Dispatchers.IO) {
            db.userProfileDao().insert(profile)
        }
        // Save to Firebase
        saveProfileToFirebase(profile)
    }

    /**
     * Retrieve user profile from local database by email
     * @param email User's email address
     * @return UserProfile object or null if not found
     */
    suspend fun getProfile(email: String): UserProfile? =
        withContext(Dispatchers.IO) {
            db.userProfileDao().getProfile(email)
        }

    /**
     * Save user profile to Firebase Realtime Database
     * @param profile UserProfile object to save
     */
    private suspend fun saveProfileToFirebase(profile: UserProfile) {
        val firebaseKey = profile.email.replace(".", "_")
        firebaseDB.child("profiles").child(firebaseKey).setValue(profile.toMap()).await()
    }

    /**
     * Retrieve user profile from Firebase by email
     * @param email User's email address
     * @return UserProfile object or null if not found
     */
    private suspend fun getProfileFromFirebase(email: String): UserProfile? {
        val firebaseKey = email.replace(".", "_")
        val snapshot = firebaseDB.child("profiles").child(firebaseKey).get().await()

        return if (snapshot.exists()) {
            snapshot.getValue(UserProfile::class.java)
        } else {
            null
        }
    }

    /**
     * Synchronize profile data from Firebase to local database
     * @param email User's email address
     */
    suspend fun syncProfileFromFirebase(email: String) {
        val profileFromFirebase = getProfileFromFirebase(email)
        profileFromFirebase?.let {
            // Update local database with Firebase data
            withContext(Dispatchers.IO) {
                db.userProfileDao().insert(it)
            }
        }
    }
}