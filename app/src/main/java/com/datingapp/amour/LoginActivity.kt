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
 * Activity for login.
 * Checks offline (Room DB) first, then retrieves user data if needed.
 * User can pick up where they left off.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnPhone: Button
    private lateinit var tvSignUp: TextView

    private lateinit var db: AppDatabase
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnPhone = findViewById(R.id.btnContinuePhone)
        tvSignUp = findViewById(R.id.tvSignUp)

        db = AppDatabase.getDatabase(this)

        btnLogin.setOnClickListener { loginUser() }
        btnGoogle.setOnClickListener {
            Toast.makeText(this, "Google sign-in (prototype)", Toast.LENGTH_SHORT).show()
        }
        btnPhone.setOnClickListener {
            startActivity(Intent(this, PhoneAuthActivity::class.java))
        }
        tvSignUp.setOnClickListener {
            startActivity(Intent(this, WelcomeSignupActivity::class.java))
        }
    }

    /**
     * Login with email/password
     */
    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (email.isEmpty()) {
            etEmail.error = "Enter email"
            etEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter valid email"
            etEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            etPassword.error = "Enter password"
            etPassword.requestFocus()
            return
        }

        val hashedPassword = Utils.hashPassword(password)

        lifecycleScope.launch {
            val user = db.userDao().getUserByEmail(email)
            if (user != null && user.passwordHash == hashedPassword) {
                // Login successful
                Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                val i = Intent(this@LoginActivity, ProfileSetupActivity::class.java)
                i.putExtra("email", email) // Pass email to retrieve user profile
                startActivity(i)
            } else {
                Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
