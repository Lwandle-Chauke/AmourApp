package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var phoneInput: EditText
    private lateinit var otpInput: EditText
    private lateinit var btnSendOtp: Button
    private lateinit var btnVerifyOtp: Button
    private lateinit var progressBar: ProgressBar

    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)

        auth = FirebaseAuth.getInstance()
        phoneInput = findViewById(R.id.etPhoneNumber)
        otpInput = findViewById(R.id.etOtp)
        btnSendOtp = findViewById(R.id.btnSendOtp)
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp)
        progressBar = findViewById(R.id.progressBar)

        btnSendOtp.setOnClickListener {
            val phone = phoneInput.text.toString().trim()
            if (phone.isNotEmpty()) sendVerificationCode(phone)
            else Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
        }

        btnVerifyOtp.setOnClickListener {
            val code = otpInput.text.toString().trim()
            if (code.isNotEmpty() && storedVerificationId != null) {
                val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        progressBar.visibility = View.VISIBLE
        btnSendOtp.isEnabled = false

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.e("PhoneAuth", "Verification failed", e)
                    progressBar.visibility = View.GONE
                    btnSendOtp.isEnabled = true
                    Toast.makeText(
                        this@PhoneAuthActivity,
                        "Verification failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d("PhoneAuth", "Code sent: $verificationId")
                    storedVerificationId = verificationId
                    resendToken = token
                    progressBar.visibility = View.GONE
                    btnSendOtp.isEnabled = true
                    Toast.makeText(
                        this@PhoneAuthActivity,
                        "OTP sent successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                Log.d("PhoneAuth", "signInWithCredential:success")
                val firebaseUser = auth.currentUser

                firebaseUser?.let { user ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val userRepository = UserRepository.getInstance(this@PhoneAuthActivity)
                        val existingUser = userRepository.getUserByUid(user.uid)

                        if (existingUser == null) {
                            val newUser = User(
                                uid = user.uid,
                                name = user.phoneNumber ?: "Phone User",
                                email = user.phoneNumber ?: "",
                                isEmailVerified = true
                            )
                            userRepository.saveUser(newUser)
                        }

                        userRepository.updateLastLogin(user.uid)

                        if (existingUser?.isProfileComplete == true) {
                            openMainApp(user.uid)
                        } else {
                            openProfileSetup(user.uid, user.phoneNumber ?: "")
                        }
                    }
                }
            } else {
                Log.e("PhoneAuth", "signInWithCredential:failure", task.exception)
                Toast.makeText(
                    this,
                    "Failed to sign in: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()
                btnSendOtp.isEnabled = true
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
