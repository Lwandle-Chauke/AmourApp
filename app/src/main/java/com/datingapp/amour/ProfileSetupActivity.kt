package com.datingapp.amour

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.data.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var etBio: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etInterests: EditText
    private lateinit var etAge: EditText
    private lateinit var btnContinue: Button

    private lateinit var db: AppDatabase
    private val firebaseDB = FirebaseDatabase.getInstance().reference

    private var email: String? = null  // Passed from signup/login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        imgProfile = findViewById(R.id.imgProfile)
        etBio = findViewById(R.id.etBio)
        rgGender = findViewById(R.id.rgGender)
        etInterests = findViewById(R.id.etInterests)
        etAge = findViewById(R.id.etAge)
        btnContinue = findViewById(R.id.btnContinue)

        db = AppDatabase.getDatabase(this)

        email = intent.getStringExtra("email")

        imgProfile.setOnClickListener {
            Toast.makeText(this, "Profile photo picker (prototype)", Toast.LENGTH_SHORT).show()
        }

        btnContinue.setOnClickListener { saveProfile() }
    }

    private fun saveProfile() {
        val bio = etBio.text.toString().trim()
        val selectedGenderId = rgGender.checkedRadioButtonId
        val interests = etInterests.text.toString().trim()
        val age = etAge.text.toString().trim()

        if (bio.isEmpty()) {
            etBio.error = "Enter a short bio"
            return
        }
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Select a gender", Toast.LENGTH_SHORT).show()
            return
        }
        if (age.isEmpty() || age.toIntOrNull() == null || age.toInt() < 18) {
            etAge.error = "Enter valid age (18+)"
            return
        }

        val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

        // Save to Firebase
        email?.let {
            val profileMap = mapOf(
                "bio" to bio,
                "gender" to gender,
                "interests" to interests,
                "age" to age
            )
            firebaseDB.child("profiles").child(it.replace(".", "_")).setValue(profileMap)
        }

        // Optional: Save locally to Room if needed

        Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()
        // Navigate to next activity (like match browsing)
    }
}
