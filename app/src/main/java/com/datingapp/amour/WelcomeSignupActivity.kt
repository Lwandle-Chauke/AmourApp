package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.data.User
import com.datingapp.amour.utils.Utils
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class WelcomeSignupActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnPhone: Button
    private lateinit var tvLogin: TextView

    private lateinit var db: AppDatabase
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_signup)

        bindViews()
        db = AppDatabase.getDatabase(this)

        // Set click listeners with safe error handling
        btnSignUp.setOnClickListener { safeCall { onSignUpClicked() } }
        btnGoogle.setOnClickListener { safeCall { openProfileSetup() } }
        btnPhone.setOnClickListener { safeCall { openPhoneAuth() } }
        tvLogin.setOnClickListener { safeCall { openLogin() } }
    }

    private fun bindViews() {
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnGoogle = findViewById(R.id.btnContinueGoogle)
        btnPhone = findViewById(R.id.btnContinuePhone)
        tvLogin = findViewById(R.id.tvLogin)
    }

    /** Safe wrapper to catch unexpected crashes and show user-friendly error messages */
    private fun safeCall(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /** Handle signup button click with validation and user creation */
    private fun onSignUpClicked() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Comprehensive form validation
        when {
            name.isEmpty() -> {
                etName.error = "Enter your full name";
                etName.requestFocus();
                return
            }
            email.isEmpty() -> {
                etEmail.error = "Enter your email";
                etEmail.requestFocus();
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Enter a valid email";
                etEmail.requestFocus();
                return
            }
            password.length < 6 -> {
                etPassword.error = "Password must be at least 6 characters";
                etPassword.requestFocus();
                return
            }
            password != confirmPassword -> {
                etConfirmPassword.error = "Passwords do not match";
                etConfirmPassword.requestFocus();
                return
            }
        }

        val hashedPassword = Utils.hashPassword(password)

        // Launch coroutine for saving user (avoiding UI thread blocking)
        lifecycleScope.launchWhenStarted {
            try {
                val user = User(name = name, email = email, passwordHash = hashedPassword)

                // Save to Room database (offline storage)
                withContext(Dispatchers.IO) {
                    db.userDao().insert(user)
                }

                // Save to Firebase Realtime Database (online storage)
                val firebaseKey = email.replace(".", "_") // Firebase doesn't allow "." in keys
                val userMap = mapOf(
                    "name" to name,
                    "email" to email,
                    "passwordHash" to hashedPassword
                )
                firebaseDB.child("users").child(firebaseKey).setValue(userMap).await()

                // Check if this is a returning user with existing profile
                val existingUser = withContext(Dispatchers.IO) {
                    db.userDao().getUserByEmail(email)
                }

                if (existingUser != null && isProfileComplete(existingUser)) {
                    // User exists and profile is complete - go directly to profile view
                    Toast.makeText(this@WelcomeSignupActivity, "Welcome back!", Toast.LENGTH_SHORT).show()
                    openProfileView(email)
                } else {
                    // New user or incomplete profile - go to profile setup
                    Toast.makeText(this@WelcomeSignupActivity, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    openProfileSetup(email)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@WelcomeSignupActivity, "Failed to create account: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    /** Check if user profile is complete (has all required fields filled) */
    private fun isProfileComplete(user: User): Boolean {
        return user.age != null &&
                user.gender != null &&
                user.orientation != null &&
                user.location != null &&
                user.profileImageUrl != null
    }

    /** Navigate to ProfileSetupActivity for new users or incomplete profiles */
    private fun openProfileSetup(email: String? = null) {
        val intent = Intent(this, ProfileSetupActivity::class.java)
        email?.let { intent.putExtra("email", it) }
        startActivity(intent)
        finish() // Prevent going back to signup screen
    }

    /** Navigate to ProfileViewActivity for returning users with complete profiles */
    private fun openProfileView(email: String? = null) {
        val intent = Intent(this, ProfileViewActivity::class.java)
        email?.let { intent.putExtra("email", it) }
        startActivity(intent)
        finish() // Prevent going back to signup screen
    }

    private fun openPhoneAuth() {
        startActivity(Intent(this, PhoneAuthActivity::class.java))
        finish()
    }

    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}