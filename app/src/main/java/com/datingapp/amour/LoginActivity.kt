package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: Button
    private lateinit var btnPhone: Button
    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogle = findViewById(R.id.btnContinueGoogle)
        btnPhone = findViewById(R.id.btnContinuePhone)

        // Google Sign-In Config
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnLogin.setOnClickListener { loginWithEmail() }
        btnGoogle.setOnClickListener { loginWithGoogle() }
        btnPhone.setOnClickListener {
            val intent = Intent(this, PhoneAuthActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithEmail() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = auth.currentUser
                firebaseUser?.let { user ->
                    lifecycleScope.launch(Dispatchers.Main) {
                        val userRepository = UserRepository.getInstance(this@LoginActivity)
                        val existingUser = userRepository.getUserByUid(user.uid)

                        if (existingUser == null) {
                            val newUser = User(
                                uid = user.uid,
                                name = user.displayName ?: "Email User",
                                email = user.email ?: "",
                                isEmailVerified = user.isEmailVerified
                            )
                            userRepository.saveUser(newUser)
                        }

                        userRepository.updateLastLogin(user.uid)

                        if (existingUser?.isProfileComplete == true) {
                            openMainApp(user.uid)
                        } else {
                            openProfileSetup(user.uid, user.email ?: "")
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Login failed: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        firebaseUser?.let { user ->
                            lifecycleScope.launch(Dispatchers.Main) {
                                val userRepository = UserRepository.getInstance(this@LoginActivity)
                                val existingUser = userRepository.getUserByUid(user.uid)

                                if (existingUser == null) {
                                    val newUser = User(
                                        uid = user.uid,
                                        name = user.displayName ?: "Google User",
                                        email = user.email ?: "",
                                        isEmailVerified = true
                                    )
                                    userRepository.saveUser(newUser)
                                }

                                userRepository.updateLastLogin(user.uid)

                                if (existingUser?.isProfileComplete == true) {
                                    openMainApp(user.uid)
                                } else {
                                    openProfileSetup(user.uid, user.email ?: "")
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Google sign-in failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "Google Sign-in failed", e)
                Toast.makeText(this, "Google Sign-in failed", Toast.LENGTH_SHORT).show()
            }
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
}
