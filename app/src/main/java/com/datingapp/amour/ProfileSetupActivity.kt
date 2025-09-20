package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var etBio: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etInterests: EditText
    private lateinit var etAge: EditText
    private lateinit var btnContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        imgProfile = findViewById(R.id.imgProfile)
        etBio = findViewById(R.id.etBio)
        rgGender = findViewById(R.id.rgGender)
        etInterests = findViewById(R.id.etInterests)
        etAge = findViewById(R.id.etAge)
        btnContinue = findViewById(R.id.btnContinue)

        imgProfile.setOnClickListener {
            Toast.makeText(this, "Profile photo picker (prototype)", Toast.LENGTH_SHORT).show()
        }

        btnContinue.setOnClickListener { onContinueClicked() }
    }

    private fun onContinueClicked() {
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
            etAge.error = "Enter a valid age (18+)"
            return
        }

        val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

        Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()

        val i = Intent(this, MatchBrowsingActivity::class.java)
        i.putExtra("bio", bio)
        i.putExtra("gender", gender)
        i.putExtra("interests", interests)
        i.putExtra("age", age)
        startActivity(i)
    }
}
