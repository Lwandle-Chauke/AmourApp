package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.data.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import java.security.MessageDigest

class WelcomeSignupActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnFacebook: Button
    private lateinit var tvLogin: TextView

    private lateinit var db: AppDatabase
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_signup)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnGoogle = findViewById(R.id.btnContinueGoogle)
        btnFacebook = findViewById(R.id.btnContinueFacebook)
        tvLogin = findViewById(R.id.tvLogin)

        db = AppDatabase.getDatabase(this)

        btnSignUp.setOnClickListener { onSignUpClicked() }
        btnGoogle.setOnClickListener { onGoogleClicked() }
        btnFacebook.setOnClickListener { onFacebookClicked() }
        tvLogin.setOnClickListener { openLogin() }
    }

    private fun onSignUpClicked() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        when {
            name.isEmpty() -> {
                etName.error = "Enter your full name"
                etName.requestFocus()
            }
            email.isEmpty() -> {
                etEmail.error = "Enter your email"
                etEmail.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Enter a valid email"
                etEmail.requestFocus()
            }
            password.length < 6 -> {
                etPassword.error = "Password must be at least 6 characters"
                etPassword.requestFocus()
            }
            password != confirmPassword -> {
                etConfirmPassword.error = "Passwords do not match"
                etConfirmPassword.requestFocus()
            }
            else -> {
                val hashedPassword = hashPassword(password)
                saveUserOffline(name, email, hashedPassword)
                saveUserOnline(name, email, hashedPassword)
                Toast.makeText(this, "Account created — proceeding to profile setup", Toast.LENGTH_SHORT).show()
                val i = Intent(this, ProfileSetupActivity::class.java)
                i.putExtra("name", name)
                i.putExtra("email", email)
                startActivity(i)
            }
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun saveUserOffline(name: String, email: String, passwordHash: String) {
        val user = User(name = name, email = email, passwordHash = passwordHash)
        lifecycleScope.launch {
            db.userDao().insert(user)
        }
    }

    private fun saveUserOnline(name: String, email: String, passwordHash: String) {
        val userMap = mapOf(
            "name" to name,
            "email" to email,
            "passwordHash" to passwordHash
        )
        firebaseDB.child("users").child(email.replace(".", "_")).setValue(userMap)
    }

    private fun onGoogleClicked() {
        Toast.makeText(this, "Google sign-in (prototype)", Toast.LENGTH_SHORT).show()
        val i = Intent(this, ProfileSetupActivity::class.java)
        startActivity(i)
    }

    private fun onFacebookClicked() {
        Toast.makeText(this, "Facebook sign-in (prototype)", Toast.LENGTH_SHORT).show()
        val i = Intent(this, ProfileSetupActivity::class.java)
        startActivity(i)
    }

    private fun openLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }
}
