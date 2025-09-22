package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.datingapp.amour.utils.Utils
import java.util.concurrent.TimeUnit

/**
 * Handles phone number authentication via Firebase.
 * Flow:
 *  1. User enters phone number
 *  2. Firebase sends OTP
 *  3. User enters OTP
 *  4. Firebase verifies and signs them in
 */
class PhoneAuthActivity : AppCompatActivity() {

    private lateinit var etPhoneNumber: EditText
    private lateinit var etOtp: EditText
    private lateinit var btnSendOtp: Button
    private lateinit var btnVerifyOtp: Button
    private lateinit var progressBar: ProgressBar

    // Nullable, because Firebase might fail before sending OTP
    private var verificationId: String? = null

    // Firebase Auth instance
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)

        // Bind UI components
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etOtp = findViewById(R.id.etOtp)
        btnSendOtp = findViewById(R.id.btnSendOtp)
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp)
        progressBar = findViewById(R.id.progressBar)

        // Hide OTP fields initially
        etOtp.visibility = View.GONE
        btnVerifyOtp.visibility = View.GONE

        btnSendOtp.setOnClickListener { sendOtp() }
        btnVerifyOtp.setOnClickListener { verifyOtp() }
    }

    /**
     * Sends OTP to entered phone number
     */
    private fun sendOtp() {
        val phoneNumber = Utils.normalizePhone(etPhoneNumber.text.toString().trim())

        if (phoneNumber.isEmpty()) {
            etPhoneNumber.error = "Enter your phone number"
            etPhoneNumber.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE

        // Configure OTP request
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)           // phone number in E.164 format (+27xxxx)
            .setTimeout(60L, TimeUnit.SECONDS)    // timeout
            .setActivity(this)                    // current activity
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                // Auto verification (instant or auto-retrieval case)
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    progressBar.visibility = View.GONE
                    Log.d("PhoneAuth", "Verification completed automatically.")
                    signInWithPhoneAuthCredential(credential)
                }

                // Failed to send/verify OTP
                override fun onVerificationFailed(e: FirebaseException) {
                    progressBar.visibility = View.GONE
                    btnSendOtp.isEnabled = true // allow retry
                    Log.e("PhoneAuth", "Verification failed", e)
                    Toast.makeText(
                        this@PhoneAuthActivity,
                        "Verification failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // OTP code sent
                override fun onCodeSent(
                    verifId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verifId, token)
                    progressBar.visibility = View.GONE

                    // Save verification ID for later
                    verificationId = verifId

                    // Show OTP input
                    etOtp.visibility = View.VISIBLE
                    btnVerifyOtp.visibility = View.VISIBLE

                    // Disable Send OTP button (avoid spam)
                    btnSendOtp.isEnabled = false

                    Toast.makeText(
                        this@PhoneAuthActivity,
                        "OTP sent to $phoneNumber",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    /**
     * Verifies OTP entered by user
     */
    private fun verifyOtp() {
        val otpCode = etOtp.text.toString().trim()
        if (otpCode.isEmpty()) {
            etOtp.error = "Enter OTP"
            etOtp.requestFocus()
            return
        }

        if (verificationId == null) {
            Toast.makeText(this, "No OTP request in progress", Toast.LENGTH_LONG).show()
            return
        }

        progressBar.visibility = View.VISIBLE
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otpCode)
        signInWithPhoneAuthCredential(credential)
    }

    /**
     * Sign in user using Firebase credential
     */
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            progressBar.visibility = View.GONE
            if (task.isSuccessful) {
                Log.d("PhoneAuth", "signInWithCredential:success")
                Toast.makeText(this, "Phone authentication successful", Toast.LENGTH_SHORT).show()

                // Go to profile setup
                startActivity(Intent(this, ProfileSetupActivity::class.java))
                finish()
            } else {
                Log.e("PhoneAuth", "signInWithCredential:failure", task.exception)
                Toast.makeText(
                    this,
                    "Failed to sign in: ${task.exception?.message}",
                    Toast.LENGTH_LONG
                ).show()

                // Allow retry
                btnSendOtp.isEnabled = true
            }
        }
    }
}
