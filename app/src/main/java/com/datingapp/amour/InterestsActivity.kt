package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.UserProfile
import com.datingapp.amour.data.UserRepository
import kotlinx.coroutines.launch

/**
 * Activity for collecting user interests, bio, and profile prompts
 * Second step in profile setup after basic information
 */
class InterestsActivity : AppCompatActivity() {

    // Data management
    private lateinit var userRepository: UserRepository
    private lateinit var userEmail: String

    // UI component declarations
    private lateinit var etBio: EditText
    private lateinit var etLifeGoal: EditText
    private lateinit var etWeekend: EditText
    private lateinit var etFriends: EditText
    private lateinit var cbFriends: CheckBox
    private lateinit var cbShortTerm: CheckBox
    private lateinit var cbLongTerm: CheckBox
    private lateinit var flexHobbies: com.google.android.flexbox.FlexboxLayout
    private lateinit var etEducation: EditText
    private lateinit var btnSaveInterests: Button
    private lateinit var btnBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        // Initialize components in order
        initializeDependencies()
        getUserEmail()
        initializeViews()
        setupClickListeners()
        loadExistingData()
    }

    /**
     * Initialize repositories and services
     */
    private fun initializeDependencies() {
        userRepository = UserRepository.getInstance(this)
    }

    /**
     * Extract user email from intent
     */
    private fun getUserEmail() {
        userEmail = intent.getStringExtra("email") ?: run {
            Toast.makeText(this, "User email not available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
    }

    /**
     * Bind XML views to Kotlin variables
     */
    private fun initializeViews() {
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
    }

    /**
     * Setup click listeners for buttons
     */
    private fun setupClickListeners() {
        // Back button - return to previous screen
        btnBack.setOnClickListener { onBackPressed() }

        // Save button - validate and save interests
        btnSaveInterests.setOnClickListener { saveProfile() }
    }

    /**
     * Load existing profile data from database
     */
    private fun loadExistingData() {
        lifecycleScope.launch {
            try {
                val profile = userRepository.getProfile(userEmail)
                profile?.let { populateUI(it) }
            } catch (e: Exception) {
                // Silent fail - it's okay if no existing data
            }
        }
    }

    /**
     * Populate UI with existing profile data
     */
    private fun populateUI(profile: UserProfile) {
        etBio.setText(profile.bio)
        etLifeGoal.setText(profile.prompt1)
        etWeekend.setText(profile.prompt2)
        etFriends.setText(profile.prompt3)
        etEducation.setText(profile.distancePreference)

        // Set looking for checkboxes
        val lookingForList = profile.agePreference.split(",")
        cbFriends.isChecked = lookingForList.contains("Friends")
        cbShortTerm.isChecked = lookingForList.contains("Short-term")
        cbLongTerm.isChecked = lookingForList.contains("Long-term")

        // Set hobby toggle buttons
        val hobbies = profile.interests.split(",")
        for (i in 0 until flexHobbies.childCount) {
            val child = flexHobbies.getChildAt(i)
            if (child is ToggleButton) {
                val hobbyText = child.textOff.toString()
                child.isChecked = hobbies.contains(hobbyText)
            }
        }
    }

    /**
     * Validate and save profile interests and prompts
     */
    private fun saveProfile() {
        // Get input values
        val bio = etBio.text.toString().trim()
        val prompt1 = etLifeGoal.text.toString().trim()
        val prompt2 = etWeekend.text.toString().trim()
        val prompt3 = etFriends.text.toString().trim()
        val education = etEducation.text.toString().trim()

        // Validate required fields
        if (!validateInput(bio, prompt1, prompt2, prompt3, education)) {
            return
        }

        // Get selected preferences
        val lookingFor = getLookingForSelection()
        val interests = getInterestsSelection()

        // Create profile object
        val profile = UserProfile(
            email = userEmail,
            bio = bio,
            gender = "", // Will be populated from User entity
            prompt1 = prompt1,
            prompt2 = prompt2,
            prompt3 = prompt3,
            interests = interests,
            agePreference = lookingFor,
            distancePreference = education
        )

        // Disable save button during processing
        setSaveButtonState(false, "Saving...")

        // Save profile in background
        lifecycleScope.launch {
            try {
                userRepository.saveUserProfile(profile)
                showSuccessAndNavigate()
            } catch (e: Exception) {
                showErrorAndResetButton(e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Validate all input fields
     */
    private fun validateInput(
        bio: String,
        prompt1: String,
        prompt2: String,
        prompt3: String,
        education: String
    ): Boolean {
        return when {
            bio.isEmpty() -> {
                etBio.error = "Please enter your bio"
                etBio.requestFocus()
                false
            }
            prompt1.isEmpty() -> {
                etLifeGoal.error = "Please complete this prompt"
                etLifeGoal.requestFocus()
                false
            }
            prompt2.isEmpty() -> {
                etWeekend.error = "Please complete this prompt"
                etWeekend.requestFocus()
                false
            }
            prompt3.isEmpty() -> {
                etFriends.error = "Please complete this prompt"
                etFriends.requestFocus()
                false
            }
            education.isEmpty() -> {
                etEducation.error = "Please enter your education/occupation"
                etEducation.requestFocus()
                false
            }
            !isAtLeastOneLookingForSelected() -> {
                Toast.makeText(this, "Please select at least one relationship goal", Toast.LENGTH_SHORT).show()
                false
            }
            !isAtLeastOneInterestSelected() -> {
                Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    /**
     * Get selected relationship goals
     */
    private fun getLookingForSelection(): String {
        val selections = mutableListOf<String>()
        if (cbFriends.isChecked) selections.add("Friends")
        if (cbShortTerm.isChecked) selections.add("Short-term")
        if (cbLongTerm.isChecked) selections.add("Long-term")
        return selections.joinToString(",")
    }

    /**
     * Get selected interests from toggle buttons
     */
    private fun getInterestsSelection(): String {
        val interests = mutableListOf<String>()
        for (i in 0 until flexHobbies.childCount) {
            val child = flexHobbies.getChildAt(i)
            if (child is ToggleButton && child.isChecked) {
                interests.add(child.text.toString())
            }
        }
        return interests.joinToString(",")
    }

    /**
     * Check if at least one relationship goal is selected
     */
    private fun isAtLeastOneLookingForSelected(): Boolean {
        return cbFriends.isChecked || cbShortTerm.isChecked || cbLongTerm.isChecked
    }

    /**
     * Check if at least one interest is selected
     */
    private fun isAtLeastOneInterestSelected(): Boolean {
        for (i in 0 until flexHobbies.childCount) {
            val child = flexHobbies.getChildAt(i)
            if (child is ToggleButton && child.isChecked) {
                return true
            }
        }
        return false
    }

    /**
     * Update save button state
     */
    private fun setSaveButtonState(enabled: Boolean, text: String) {
        btnSaveInterests.isEnabled = enabled
        btnSaveInterests.text = text
    }

    /**
     * Handle successful save operation
     */
    private fun showSuccessAndNavigate() {
        runOnUiThread {
            Toast.makeText(this, "Interests saved successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfileViewActivity::class.java)
            intent.putExtra("email", userEmail)
            startActivity(intent)
            finish() // Close interests activity
        }
    }

    /**
     * Handle errors during save operation
     */
    private fun showErrorAndResetButton(errorMessage: String) {
        runOnUiThread {
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            setSaveButtonState(true, "Save & Continue")
        }
    }
}