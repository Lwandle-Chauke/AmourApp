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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Activity for user authentication/login
 * Handles user login and redirects to appropriate activity based on profile completion
 */
class LoginActivity : AppCompatActivity() {

    // UI component declarations
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView

    // Database reference
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize UI components
        initializeViews()

        // Setup click listeners
        setupClickListeners()
    }

    /**
     * Bind XML views to Kotlin variables
     */
    private fun initializeViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvSignUp = findViewById(R.id.tvSignUp)

        // Initialize Room database
        db = AppDatabase.getDatabase(this)
    }

    /**
     * Setup button click listeners
     */
    private fun setupClickListeners() {
        btnLogin.setOnClickListener { loginUser() }
        tvSignUp.setOnClickListener {
            // Navigate to registration screen
            startActivity(Intent(this, WelcomeSignupActivity::class.java))
        }
    }

    /**
     * Handle user authentication process
     * Validates credentials and navigates to appropriate activity
     */
    private fun loginUser() {
        // Get input values
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Validate input fields
        if (!validateInput(email, password)) {
            return // Stop if validation fails
        }

        // Hash password for comparison
        val hashedPassword = Utils.hashPassword(password)

        // Use lifecycleScope for coroutine that follows activity lifecycle
        lifecycleScope.launchWhenStarted {
            // Retrieve user from database on background thread
            val user = withContext(Dispatchers.IO) {
                db.userDao().getUserByEmail(email)
            }

            // Check if user exists and password matches
            if (user != null && user.passwordHash == hashedPassword) {
                // Login successful
                Toast.makeText(
                    this@LoginActivity,
                    "Login successful!",
                    Toast.LENGTH_SHORT
                ).show()

                // Navigate based on profile completion status
                if (isProfileComplete(user)) {
                    openProfileView(email) // Profile is complete
                } else {
                    openProfileSetup(email) // Profile needs setup
                }
            } else {
                // Invalid credentials
                Toast.makeText(
                    this@LoginActivity,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Validate email and password input
     * @return true if valid, false otherwise
     */
    private fun validateInput(email: String, password: String): Boolean {
        return when {
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
            password.isEmpty() -> {
                etPassword.error = "Please enter your password"
                etPassword.requestFocus()
                false
            }
            else -> true // All validations passed
        }
    }

    /**
     * Check if user profile is complete
     * @return true if all required profile fields are filled
     */
    private fun isProfileComplete(user: User): Boolean {
        return user.age != null &&
                !user.gender.isNullOrEmpty() &&
                !user.orientation.isNullOrEmpty() &&
                !user.location.isNullOrEmpty() &&
                !user.profileImageUrl.isNullOrEmpty()
    }

    /**
     * Navigate to ProfileSetupActivity for incomplete profiles
     */
    private fun openProfileSetup(email: String) {
        val intent = Intent(this, ProfileSetupActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish() // Close login activity
    }

    /**
     * Navigate to ProfileViewActivity for complete profiles
     */
    private fun openProfileView(email: String) {
        val intent = Intent(this, ProfileViewActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish() // Close login activity
    }
}