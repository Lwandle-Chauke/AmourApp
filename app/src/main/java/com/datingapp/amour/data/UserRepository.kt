package com.datingapp.amour.data

import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository for managing user and user profile data.
 * Handles saving to BOTH Room (offline) and Firebase (online).
 * Also supports syncing Firebase → Room for keeping local data fresh.
 */
class UserRepository private constructor(context: Context) {

    // Local database
    private val db = AppDatabase.getDatabase(context)
    private val userDao = db.userDao()
    private val profileDao = db.userProfileDao()

    // Firebase reference
    private val firebaseDb: DatabaseReference = FirebaseDatabase.getInstance().reference

    /**
     * Save user to Room and Firebase.
     */
    fun saveUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Save to Room (offline)
                userDao.insert(user)

                // Save to Firebase (online)
                firebaseDb.child("users")
                    .child(user.email.replace(".", "_")) // Firebase doesn't allow "."
                    .setValue(user.toMap())
                    .addOnFailureListener { e ->
                        Log.e("UserRepository", "Failed to save user to Firebase: ${e.message}")
                    }

            } catch (e: Exception) {
                Log.e("UserRepository", "Error saving user: ${e.message}")
            }
        }
    }

    /**
     * Save user profile to Room and Firebase.
     */
    fun saveUserProfile(profile: UserProfile) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Save to Room (offline)
                profileDao.insert(profile)

                // Save to Firebase (online)
                firebaseDb.child("user_profiles")
                    .child(profile.email.replace(".", "_"))
                    .setValue(profile.toMap())
                    .addOnFailureListener { e ->
                        Log.e("UserRepository", "Failed to save profile to Firebase: ${e.message}")
                    }

            } catch (e: Exception) {
                Log.e("UserRepository", "Error saving profile: ${e.message}")
            }
        }
    }

    /**
     * Fetch user from local DB (offline-first).
     */
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    /**
     * Fetch profile from local DB (offline-first).
     */
    suspend fun getProfile(email: String): UserProfile? {
        return profileDao.getProfile(email)
    }

    /**
     * Sync latest user data from Firebase → Room.
     * Keeps local DB fresh when online.
     */
    fun syncUserFromFirebase(email: String) {
        val safeKey = email.replace(".", "_")
        firebaseDb.child("users").child(safeKey)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(User::class.java)?.let { user ->
                        CoroutineScope(Dispatchers.IO).launch {
                            userDao.insert(user)
                            Log.d("UserRepository", "User synced from Firebase → Room")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("UserRepository", "Firebase user sync cancelled: ${error.message}")
                }
            })
    }

    /**
     * Sync latest user profile data from Firebase → Room.
     */
    fun syncProfileFromFirebase(email: String) {
        val safeKey = email.replace(".", "_")
        firebaseDb.child("user_profiles").child(safeKey)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(UserProfile::class.java)?.let { profile ->
                        CoroutineScope(Dispatchers.IO).launch {
                            profileDao.insert(profile)
                            Log.d("UserRepository", "Profile synced from Firebase → Room")
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("UserRepository", "Firebase profile sync cancelled: ${error.message}")
                }
            })
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        /**
         * Singleton pattern: get repository instance.
         */
        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
