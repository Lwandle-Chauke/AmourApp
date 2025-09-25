package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserRepository
import com.datingapp.amour.utils.FirebaseAuthHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*
import java.util.*

class WelcomeSignupActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnSignUp: MaterialButton
    private lateinit var btnContinueGoogle: MaterialButton
    private lateinit var btnContinuePhone: MaterialButton
    private lateinit var tvLogin: android.widget.TextView

    private lateinit var userRepository: UserRepository
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private companion object {
        private const val RC_GOOGLE_SIGN_IN = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_signup)

        userRepository = UserRepository.getInstance(this)
        setupGoogleSignIn()

        initializeViews()
        setupClickListeners()
        checkAutoLogin()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // You need to add this to your strings.xml
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun initializeViews() {
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnContinueGoogle = findViewById(R.id.btnContinueGoogle)
        btnContinuePhone = findViewById(R.id.btnContinuePhone)
        tvLogin = findViewById(R.id.tvLogin)
    }

    private fun setupClickListeners() {
        btnSignUp.setOnClickListener { signUpWithEmail() }
        btnContinueGoogle.setOnClickListener { signInWithGoogle() }
        btnContinuePhone.setOnClickListener {
            startActivity(Intent(this, PhoneAuthActivity::class.java))
        }
        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkAutoLogin() {
        val currentUser = FirebaseAuthHelper.getCurrentUser()
        currentUser?.let { user ->
            CoroutineScope(Dispatchers.Main).launch {
                redirectLoggedInUser(user.uid)
            }
        }
    }

    private fun signUpWithEmail() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim().lowercase(Locale.getDefault())
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (!validateInput(name, email, password, confirmPassword)) {
            return
        }

        btnSignUp.isEnabled = false
        btnSignUp.text = "Creating account..."

        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (!FirebaseAuthHelper.isValidEmail(email)) {
                    etEmail.error = "Please enter a valid email address"
                    resetButton()
                    return@launch
                }

                val passwordValidation = FirebaseAuthHelper.isValidPassword(password)
                if (!passwordValidation.isValid) {
                    etPassword.error = passwordValidation.message
                    resetButton()
                    return@launch
                }

                // Check if user already exists
                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser != null) {
                    // User exists, redirect to login
                    Toast.makeText(
                        this@WelcomeSignupActivity,
                        "Account already exists. Please login.",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@WelcomeSignupActivity, LoginActivity::class.java))
                    finish()
                    return@launch
                }

                val result = FirebaseAuthHelper.registerWithEmail(email, password, name)

                if (result.isSuccess) {
                    val user = result.getOrNull()!!
                    val userData = User(
                        uid = user.uid,
                        name = name,
                        email = email,
                        isEmailVerified = user.isEmailVerified
                    )

                    val saveResult = userRepository.saveUser(userData)
                    if (saveResult.isSuccess) {
                        Toast.makeText(
                            this@WelcomeSignupActivity,
                            "Account created successfully!",
                            Toast.LENGTH_LONG
                        ).show()

                        userRepository.updateLastLogin(user.uid)
                        openProfileSetup(user.uid, email)
                    } else {
                        throw saveResult.exceptionOrNull() ?: Exception("Failed to save user data")
                    }
                } else {
                    // Handle specific Firebase errors
                    val error = result.exceptionOrNull()
                    if (error?.message?.contains("already in use") == true) {
                        // Account already exists - redirect to login
                        Toast.makeText(
                            this@WelcomeSignupActivity,
                            "Account already exists. Please login.",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@WelcomeSignupActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        throw error ?: Exception("Registration failed")
                    }
                }
            } catch (e: Exception) {
                Log.e("SignUp", "Error creating account", e)
                handleSignUpError(e)
                resetButton()
            }
        }
    }

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
            password.isEmpty() -> {
                etPassword.error = "Please enter a password"
                etPassword.requestFocus()
                false
            }
            password != confirmPassword -> {
                etConfirmPassword.error = "Passwords do not match"
                etConfirmPassword.requestFocus()
                false
            }
            else -> true
        }
    }

    private fun handleSignUpError(e: Exception) {
        val errorMessage = when {
            e.message?.contains("email address is already in use") == true -> {
                "This email is already registered. Please try logging in."
            }
            e.message?.contains("password is invalid") == true -> {
                "Password is too weak. Please use a stronger password."
            }
            e.message?.contains("network error") == true -> {
                "Network error. Please check your internet connection."
            }
            else -> "Registration failed: ${e.message ?: "Unknown error"}"
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun resetButton() {
        btnSignUp.isEnabled = true
        btnSignUp.text = "Sign up with Email"
    }

    private suspend fun redirectLoggedInUser(uid: String) {
        val user = userRepository.getUserByUid(uid)
        if (user?.isProfileComplete == true) {
            openMainApp(uid)
        } else {
            openProfileSetup(uid, user?.email ?: "")
        }
    }

    private fun openProfileSetup(uid: String, email: String) {
        val intent = Intent(this, ProfileSetupActivity::class.java)
        intent.putExtra("uid", uid)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

    private fun openMainApp(uid: String) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("uid", uid)
        startActivity(intent)
        finish()
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.w("GoogleSignIn", "Google sign in failed", e)
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        // Check if this is a new user or existing
                        CoroutineScope(Dispatchers.Main).launch {
                            checkGoogleUserProfile(it.uid, it.email ?: "", it.displayName ?: "")
                        }
                    }
                } else {
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private suspend fun checkGoogleUserProfile(uid: String, email: String, name: String) {
        val existingUser = userRepository.getUserByUid(uid)

        if (existingUser == null) {
            // New user - create account and go to profile setup
            val userData = User(
                uid = uid,
                name = name,
                email = email,
                isEmailVerified = true
            )

            userRepository.saveUser(userData)
            userRepository.updateLastLogin(uid)
            openProfileSetup(uid, email)
        } else {
            // Existing user - check profile completion
            userRepository.updateLastLogin(uid)
            if (existingUser.isProfileComplete) {
                openMainApp(uid)
            } else {
                openProfileSetup(uid, email)
            }
        }
    }
}