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
import kotlinx.coroutines.launch

/**
 * Activity for user signup.
 * Saves user both offline (Room) and online (Firebase Realtime Database)
 */
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

        // Bind UI
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnGoogle = findViewById(R.id.btnContinueGoogle)
        btnPhone = findViewById(R.id.btnContinuePhone)
        tvLogin = findViewById(R.id.tvLogin)

        db = AppDatabase.getDatabase(this)

        // Set button listeners
        btnSignUp.setOnClickListener { onSignUpClicked() }
        btnGoogle.setOnClickListener { onGoogleClicked() }
        btnPhone.setOnClickListener { onPhoneClicked() }
        tvLogin.setOnClickListener { openLogin() }
    }

    /**
     * Handle email/password signup
     */
    private fun onSignUpClicked() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // Validation
        when {
            name.isEmpty() -> { etName.error = "Enter your full name"; etName.requestFocus() }
            email.isEmpty() -> { etEmail.error = "Enter your email"; etEmail.requestFocus() }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> { etEmail.error = "Enter a valid email"; etEmail.requestFocus() }
            password.length < 6 -> { etPassword.error = "Password must be at least 6 characters"; etPassword.requestFocus() }
            password != confirmPassword -> { etConfirmPassword.error = "Passwords do not match"; etConfirmPassword.requestFocus() }
            else -> {
                val hashedPassword = Utils.hashPassword(password)
                saveUserOffline(name, email, hashedPassword)
                saveUserOnline(name, email, hashedPassword)

                Toast.makeText(this, "Account created — proceeding to profile setup", Toast.LENGTH_SHORT).show()

                // Open ProfileSetupActivity and pass the email (used to retrieve user later)
                val i = Intent(this, ProfileSetupActivity::class.java)
                i.putExtra("email", email)
                startActivity(i)
            }
        }
    }

    /**
     * Save user in Room DB (offline)
     */
    private fun saveUserOffline(name: String, email: String?, passwordHash: String?) {
        val user = User(name = name, email = email, passwordHash = passwordHash)
        lifecycleScope.launch {
            db.userDao().insert(user)
        }
    }

    /**
     * Save user in Firebase Realtime Database (online)
     */
    private fun saveUserOnline(name: String, email: String?, passwordHash: String?) {
        val userMap = mapOf(
            "name" to name,
            "email" to email,
            "passwordHash" to passwordHash
        )
        email?.let {
            firebaseDB.child("users").child(it.replace(".", "_")).setValue(userMap)
        }
    }

    private fun onGoogleClicked() {
        Toast.makeText(this, "Google sign-in (prototype)", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, ProfileSetupActivity::class.java))
    }

    private fun onPhoneClicked() {
        startActivity(Intent(this, PhoneAuthActivity::class.java))
    }

    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
