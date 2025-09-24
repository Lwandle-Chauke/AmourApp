package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnPhone: Button
    private lateinit var tvSignUp: TextView

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()
        db = AppDatabase.getDatabase(this)

        // Set click listeners with error handling
        btnLogin.setOnClickListener { safeCall { loginUser() } }
        btnGoogle.setOnClickListener { safeCall { openProfileSetup() } }
        btnPhone.setOnClickListener { safeCall { openPhoneAuth() } }
        tvSignUp.setOnClickListener { safeCall { openSignUp() } }
    }

    private fun bindViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnPhone = findViewById(R.id.btnContinuePhone)
        tvSignUp = findViewById(R.id.tvSignUp)
    }

    /** Safe wrapper to prevent app crashes from unexpected exceptions */
    private fun safeCall(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /** Authenticate user and navigate to appropriate screen based on profile completion */
    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        // Input validation
        when {
            email.isEmpty() -> {
                etEmail.error = "Enter email";
                etEmail.requestFocus();
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Enter valid email";
                etEmail.requestFocus();
                return
            }
            password.isEmpty() -> {
                etPassword.error = "Enter password";
                etPassword.requestFocus();
                return
            }
        }

        val hashedPassword = Utils.hashPassword(password)

        // Perform login in background thread to avoid blocking UI
        lifecycleScope.launchWhenStarted {
            try {
                val user = withContext(Dispatchers.IO) {
                    db.userDao().getUserByEmail(email)
                }

                if (user != null && user.passwordHash == hashedPassword) {
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Check if user profile is complete to determine navigation
                    if (isProfileComplete(user)) {
                        // Profile complete - go directly to main app (ProfileView)
                        openProfileView(email)
                    } else {
                        // Profile incomplete - redirect to profile setup
                        openProfileSetup(email)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@LoginActivity, "Login error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    /** Determine if user has completed their profile setup */
    private fun isProfileComplete(user: com.datingapp.amour.data.User): Boolean {
        return user.age != null &&
                user.gender != null &&
                user.orientation != null &&
                user.location != null &&
                user.profileImageUrl != null
    }

    /** Navigate to ProfileSetupActivity for incomplete profiles */
    private fun openProfileSetup(email: String? = null) {
        val intent = Intent(this, ProfileSetupActivity::class.java)
        email?.let { intent.putExtra("email", it) }
        startActivity(intent)
        finish() // Close login activity to prevent back navigation
    }

    /** Navigate to ProfileViewActivity for complete profiles */
    private fun openProfileView(email: String? = null) {
        val intent = Intent(this, ProfileViewActivity::class.java)
        email?.let { intent.putExtra("email", it) }
        startActivity(intent)
        finish() // Close login activity to prevent back navigation
    }

    private fun openPhoneAuth() {
        startActivity(Intent(this, PhoneAuthActivity::class.java))
        finish()
    }

    private fun openSignUp() {
        startActivity(Intent(this, WelcomeSignupActivity::class.java))
        finish()
    }
}