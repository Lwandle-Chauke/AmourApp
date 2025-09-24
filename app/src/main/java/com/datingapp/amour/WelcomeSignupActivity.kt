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

/**
 * Activity for user registration/signup
 * Handles user account creation and initial profile setup navigation
 */
class WelcomeSignupActivity : AppCompatActivity() {

    // UI component declarations
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var tvLogin: TextView

    // Database references
    private lateinit var db: AppDatabase
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_signup)

        // Initialize UI components
        initializeViews()

        // Setup click listeners
        setupClickListeners()
    }

    /**
     * Bind XML views to Kotlin variables
     */
    private fun initializeViews() {
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        tvLogin = findViewById(R.id.tvLogin)

        // Initialize Room database
        db = AppDatabase.getDatabase(this)
    }

    /**
     * Setup button click listeners
     */
    private fun setupClickListeners() {
        btnSignUp.setOnClickListener { signUpUser() }
        tvLogin.setOnClickListener {
            // Navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close current activity
        }
    }

    /**
     * Handle user registration process
     * Validates input, creates user account, and navigates to profile setup
     */
    private fun signUpUser() {
        // Get input values
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Validate input fields
        if (!validateInput(name, email, password, confirmPassword)) {
            return // Stop if validation fails
        }

        // Hash password for security
        val hashedPassword = Utils.hashPassword(password)

        // Use lifecycleScope for coroutine that follows activity lifecycle
        lifecycleScope.launchWhenStarted {
            try {
                // Create new user object
                val user = User(
                    name = name,
                    email = email,
                    passwordHash = hashedPassword
                )

                // Save to local database (Room) on background thread
                withContext(Dispatchers.IO) {
                    db.userDao().insert(user)
                }

                // Save to Firebase Realtime Database
                val firebaseKey = email.replace(".", "_") // Firebase doesn't allow '.' in keys
                firebaseDB.child("users").child(firebaseKey).setValue(user.toMap()).await()

                // Show success message and navigate to profile setup
                Toast.makeText(
                    this@WelcomeSignupActivity,
                    "Account created successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                openProfileSetup(email)

            } catch (e: Exception) {
                // Handle errors during registration
                Toast.makeText(
                    this@WelcomeSignupActivity,
                    "Registration failed: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Validate user input fields
     * @return true if all validations pass, false otherwise
     */
    private fun validateInput(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return when {
            name.isEmpty() -> {
                etName.error = "Please enter your full name"
                etName.requestFocus()
                false
            }
            email.isEmpty() -> {
                etEmail.error = "Please enter your email address"
                etEmail.requestFocus()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Please enter a valid email address"
                etEmail.requestFocus()
                false
            }
            password.length < 6 -> {
                etPassword.error = "Password must be at least 6 characters long"
                etPassword.requestFocus()
                false
            }
            password != confirmPassword -> {
                etConfirmPassword.error = "Passwords do not match"
                etConfirmPassword.requestFocus()
                false
            }
            else -> true // All validations passed
        }
    }

    /**
     * Navigate to ProfileSetupActivity with user email
     */
    private fun openProfileSetup(email: String) {
        val intent = Intent(this, ProfileSetupActivity::class.java)
        intent.putExtra("email", email) // Pass email to next activity
        startActivity(intent)
        // Don't call finish() so user can navigate back if needed
    }
}