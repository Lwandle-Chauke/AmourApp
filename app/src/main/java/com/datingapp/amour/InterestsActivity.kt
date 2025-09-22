package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.data.UserProfile
import com.google.android.flexbox.FlexboxLayout
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

/**
 * Activity where users fill in their interests, bio, prompts, and education/occupation.
 * Saves data to RoomDB and Firebase.
 */
class ActivityInterests : AppCompatActivity() {

    // Room database instance
    private lateinit var db: AppDatabase

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

    // Email of current user passed from previous activity
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests) // Link to your XML layout

        // Initialize RoomDB
        db = AppDatabase.getDatabase(this)

        // Retrieve email from ProfileSetupActivity
        userEmail = intent.getStringExtra("userEmail") ?: ""

        // Bind UI elements
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

        // Load existing profile data if available
        loadProfile()

        // Back button listener
        btnBack.setOnClickListener { finish() }

        // Save & Continue listener
        btnSaveInterests.setOnClickListener { saveProfile() }
    }

    /**
     * Loads existing profile data from RoomDB and populates UI fields.
     */
    private fun loadProfile() {
        lifecycleScope.launch {
            val profile = db.userProfileDao().getProfile(userEmail)
            profile?.let {
                etBio.setText(it.bio)
                etLifeGoal.setText(it.prompt1)
                etWeekend.setText(it.prompt2)
                etFriends.setText(it.prompt3)

                // Load "Looking For" options (stored as CSV)
                val lookingFor = it.agePreference.split(",")
                cbFriends.isChecked = lookingFor.contains("Friends")
                cbShortTerm.isChecked = lookingFor.contains("Short-term")
                cbLongTerm.isChecked = lookingFor.contains("Long-term")

                // Load hobbies/interests (stored as CSV)
                val hobbies = it.interests.split(",")
                for (i in 0 until flexHobbies.childCount) {
                    val child = flexHobbies.getChildAt(i)
                    if (child is ToggleButton) {
                        child.isChecked = hobbies.contains(child.textOff.toString())
                    }
                }

                // Load education/occupation (stored in distancePreference field)
                etEducation.setText(it.distancePreference)
            }
        }
    }

    /**
     * Saves profile data to RoomDB and Firebase Realtime Database.
     */
    private fun saveProfile() {
        val bio = etBio.text.toString()
        val prompt1 = etLifeGoal.text.toString()
        val prompt2 = etWeekend.text.toString()
        val prompt3 = etFriends.text.toString()

        // Collect "Looking For" selections
        val lookingForList = mutableListOf<String>()
        if (cbFriends.isChecked) lookingForList.add("Friends")
        if (cbShortTerm.isChecked) lookingForList.add("Short-term")
        if (cbLongTerm.isChecked) lookingForList.add("Long-term")
        val lookingForCSV = lookingForList.joinToString(",")

        // Collect selected hobbies
        val hobbiesList = mutableListOf<String>()
        for (i in 0 until flexHobbies.childCount) {
            val child = flexHobbies.getChildAt(i)
            if (child is ToggleButton && child.isChecked) {
                hobbiesList.add(child.textOff.toString())
            }
        }
        val hobbiesCSV = hobbiesList.joinToString(",")

        val education = etEducation.text.toString()

        // Create UserProfile object
        val profile = UserProfile(
            email = userEmail,
            bio = bio,
            gender = "", // Already saved in ProfileSetupActivity
            prompt1 = prompt1,
            prompt2 = prompt2,
            prompt3 = prompt3,
            interests = hobbiesCSV,
            agePreference = lookingForCSV,
            distancePreference = education
        )

        // Save to RoomDB
        lifecycleScope.launch {
            db.userProfileDao().insert(profile)
        }

        // Save to Firebase
        val firebaseRef = FirebaseDatabase.getInstance().getReference("user_profiles")
        firebaseRef.child(userEmail.replace(".", "_")).setValue(profile)

        // Navigate to ProfileViewActivity
        val intent = Intent(this, ProfileViewActivity::class.java)
        intent.putExtra("userEmail", userEmail)
        startActivity(intent)
        finish()
    }
}
