package com.datingapp.amour.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

object FirebaseAuthHelper {
    private const val TAG = "FirebaseAuthHelper"
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    /**
     * Register new user with email and password
     */
    suspend fun registerWithEmail(
        email: String,
        password: String,
        name: String
    ): Result<FirebaseUser> {
        return try {
            // Create user in Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("User creation failed")

            // Update user profile with display name
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            user.updateProfile(profileUpdates).await()

            // Send email verification
            user.sendEmailVerification().await()

            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Registration failed", e)
            Result.failure(e)
        }
    }

    /**
     * Login user with email and password
     */
    suspend fun loginWithEmail(
        email: String,
        password: String
    ): Result<FirebaseUser> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("Login failed")

            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Login failed", e)
            Result.failure(e)
        }
    }

    /**
     * Check if user is logged in (for auto-login)
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Check if email is verified
     */
    fun isEmailVerified(): Boolean {
        return auth.currentUser?.isEmailVerified ?: false
    }

    /**
     * Send email verification
     */
    suspend fun sendEmailVerification(): Result<Boolean> {
        return try {
            val user = auth.currentUser ?: throw Exception("No user logged in")
            user.sendEmailVerification().await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Reset password
     */
    suspend fun resetPassword(email: String): Result<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Logout user
     */
    fun logout() {
        auth.signOut()
    }

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    /**
     * Validate password strength
     */
    fun isValidPassword(password: String): PasswordValidation {
        return when {
            password.length < 6 -> PasswordValidation(false, "Password must be at least 6 characters")
            !password.any { it.isDigit() } -> PasswordValidation(false, "Password must contain at least one number")
            !password.any { it.isLetter() } -> PasswordValidation(false, "Password must contain at least one letter")
            else -> PasswordValidation(true, "Password is valid")
        }
    }

    data class PasswordValidation(val isValid: Boolean, val message: String)
}