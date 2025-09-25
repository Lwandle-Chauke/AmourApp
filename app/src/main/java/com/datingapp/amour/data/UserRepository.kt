package com.datingapp.amour.data

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository private constructor(context: Context) {

    private val database = FirebaseDatabase.getInstance().reference
    private val usersRef = database.child("users")
    private val profilesRef = database.child("profiles")

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * Save user to Firebase Realtime Database
     */
    suspend fun saveUser(user: User): Result<Boolean> {
        return try {
            usersRef.child(user.uid).setValue(user.toMap()).await()
            Result.success(true)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error saving user", e)
            Result.failure(e)
        }
    }

    /**
     * Get user by UID
     */
    suspend fun getUserByUid(uid: String): User? {
        return try {
            val snapshot = usersRef.child(uid).get().await()
            if (snapshot.exists()) {
                User.fromMap(snapshot.value as Map<String, Any>)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error getting user", e)
            null
        }
    }

    /**
     * Get user by email
     */
    suspend fun getUserByEmail(email: String): User? {
        return try {
            val snapshot = usersRef.get().await()
            for (child in snapshot.children) {
                val user = User.fromMap(child.value as Map<String, Any>)
                if (user.email.equals(email, ignoreCase = true)) {
                    return user
                }
            }
            null
        } catch (e: Exception) {
            Log.e("UserRepository", "Error getting user by email", e)
            null
        }
    }

    /**
     * Update user's last login timestamp
     */
    suspend fun updateLastLogin(uid: String): Result<Boolean> {
        return try {
            usersRef.child(uid).child("lastLogin").setValue(System.currentTimeMillis()).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mark user profile as complete
     */
    suspend fun markProfileComplete(uid: String): Result<Boolean> {
        return try {
            usersRef.child(uid).child("isProfileComplete").setValue(true).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Observe user data changes in real-time
     */
    fun observeUser(uid: String): Flow<User?> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = User.fromMap(snapshot.value as Map<String, Any>)
                    trySend(user)
                } else {
                    trySend(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        usersRef.child(uid).addValueEventListener(listener)

        awaitClose {
            usersRef.child(uid).removeEventListener(listener)
        }
    }

    // UserProfile methods - FIXED to use uid
    suspend fun saveUserProfile(profile: UserProfile): Result<Boolean> {
        return try {
            profilesRef.child(profile.uid).setValue(profile.toMap()).await()
            Result.success(true)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error saving user profile", e)
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            val snapshot = profilesRef.child(uid).get().await()
            if (snapshot.exists()) {
                UserProfile.fromMap(snapshot.value as Map<String, Any>)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error getting user profile", e)
            null
        }
    }

    // Add method to get profile by email if needed
    suspend fun getProfileByEmail(email: String): UserProfile? {
        return try {
            val snapshot = profilesRef.get().await()
            for (child in snapshot.children) {
                val profile = UserProfile.fromMap(child.value as Map<String, Any>)
                if (profile.email.equals(email, ignoreCase = true)) {
                    return profile
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }
}