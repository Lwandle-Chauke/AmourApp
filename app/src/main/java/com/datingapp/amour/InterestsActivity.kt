package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.UserProfile
import com.datingapp.amour.data.UserRepository
import com.google.android.flexbox.FlexboxLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class InterestsActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var auth: FirebaseAuth

    // UI elements
    private lateinit var etBio: EditText
    private lateinit var etLifeGoal: EditText
    private lateinit var etWeekend: EditText
    private lateinit var etFriends: EditText
    private lateinit var cbFriends: CheckBox
    private lateinit var cbShortTerm: CheckBox
    private lateinit var cbLongTerm: CheckBox
    private lateinit var flexHobbies: FlexboxLayout
    private lateinit var etEducation: EditText
    private lateinit var btnSaveInterests: Button
    private lateinit var btnBack: ImageView

    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        initializeDependencies()
        getUserEmail()
        initializeUI()
        loadExistingData()
    }

    private fun initializeDependencies() {
        userRepository = UserRepository.getInstance(this)
        auth = FirebaseAuth.getInstance()
    }

    private fun getUserEmail() {
        userEmail = auth.currentUser?.email ?: intent.getStringExtra("email") ?: run {
            Toast.makeText(this, "User email not available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
    }

    private fun initializeUI() {
        etBio = findViewById(R.id.etBio)
        etLifeGoal = findViewById(R.id.etLifeGoal)
        etWeekend = findViewById(R.id.etWeekend)
        etFriends = findViewById(R.id.etFriends)
        cbFriends = findViewById(R.id.cbFriends)
        cbShortTerm = findViewById(R.id.cbShortTerm)
        cbLongTerm = findViewById(R.id.cbLongTerm)
        flexHobbies = findViewById(R.id.flowHobbies)
        etEducation = findViewById(R.id.etEducation)
        btnSaveInterests = findViewById(R.id.btnSaveInterests)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener { finish() }
        btnSaveInterests.setOnClickListener { saveProfile() }
    }

    private fun loadExistingData() {
        lifecycleScope.launch {
            val profile = userRepository.getProfile(userEmail)
            profile?.let { populateUI(it) }
        }
    }

    private fun populateUI(profile: UserProfile) {
        etBio.setText(profile.bio)
        etLifeGoal.setText(profile.prompt1)
        etWeekend.setText(profile.prompt2)
        etFriends.setText(profile.prompt3)
        etEducation.setText(profile.distancePreference)

        val lookingForList = profile.agePreference.split(",")
        cbFriends.isChecked = lookingForList.contains("Friends")
        cbShortTerm.isChecked = lookingForList.contains("Short-term")
        cbLongTerm.isChecked = lookingForList.contains("Long-term")

        val hobbies = profile.interests.split(",")
        for (i in 0 until flexHobbies.childCount) {
            val child = flexHobbies.getChildAt(i)
            if (child is ToggleButton) child.isChecked = hobbies.contains(child.textOff.toString())
        }
    }

    private fun saveProfile() {
        val bio = etBio.text.toString().trim()
        val prompt1 = etLifeGoal.text.toString().trim()
        val prompt2 = etWeekend.text.toString().trim()
        val prompt3 = etFriends.text.toString().trim()
        val education = etEducation.text.toString().trim()

        if (bio.isEmpty() || prompt1.isEmpty() || prompt2.isEmpty() || prompt3.isEmpty() || education.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val lookingForList = mutableListOf<String>()
        if (cbFriends.isChecked) lookingForList.add("Friends")
        if (cbShortTerm.isChecked) lookingForList.add("Short-term")
        if (cbLongTerm.isChecked) lookingForList.add("Long-term")
        val lookingFor = lookingForList.joinToString(",")

        val hobbiesList = mutableListOf<String>()
        for (i in 0 until flexHobbies.childCount) {
            val child = flexHobbies.getChildAt(i)
            if (child is ToggleButton && child.isChecked) hobbiesList.add(child.textOff.toString())
        }
        val interests = hobbiesList.joinToString(",")

        val profile = UserProfile(
            email = userEmail,
            bio = bio,
            gender = "", // gender stays in User entity
            prompt1 = prompt1,
            prompt2 = prompt2,
            prompt3 = prompt3,
            interests = interests,
            agePreference = lookingFor,
            distancePreference = education
        )

        btnSaveInterests.isEnabled = false
        btnSaveInterests.text = "Saving..."

        lifecycleScope.launch {
            try {
                userRepository.saveUserProfile(profile)
                runOnUiThread {
                    Toast.makeText(this@InterestsActivity, "Profile saved!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@InterestsActivity, ProfileViewActivity::class.java)
                    intent.putExtra("email", userEmail)
                    startActivity(intent)
                    finish()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@InterestsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    btnSaveInterests.isEnabled = true
                    btnSaveInterests.text = "Save & Continue"
                }
            }
        }
    }
}
