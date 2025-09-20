package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WelcomeSignupActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnFacebook: Button
    private lateinit var tvLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_signup)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnGoogle = findViewById(R.id.btnContinueGoogle)
        btnFacebook = findViewById(R.id.btnContinueFacebook)
        tvLogin = findViewById(R.id.tvLogin)

        btnSignUp.setOnClickListener { onSignUpClicked() }
        btnGoogle.setOnClickListener { onGoogleClicked() }
        btnFacebook.setOnClickListener { onFacebookClicked() }
        tvLogin.setOnClickListener { openLogin() }
    }

    private fun onSignUpClicked() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

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
            else -> {
                Toast.makeText(this, "Account created — proceeding to profile setup", Toast.LENGTH_SHORT).show()
                val i = Intent(this, ProfileSetupActivity::class.java)
                i.putExtra("name", name)
                i.putExtra("email", email)
                startActivity(i)
            }
        }
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
