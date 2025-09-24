package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserProfile
import com.datingapp.amour.data.UserRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class ProfileViewActivity : AppCompatActivity() {

    // Repositories and Firebase
    private lateinit var userRepository: UserRepository
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage

    // UI elements
    private lateinit var photoContainer: LinearLayout
    private lateinit var tvNameAge: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvOrientation: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvPrompts: TextView
    private lateinit var tvLookingFor: TextView
    private lateinit var tvInterests: TextView
    private lateinit var tvEducation: TextView
    private lateinit var tvLocation: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnSaveContinue: Button
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)

        initializeDependencies()
        initializeUI()
        setupNavigation()
        loadProfileData()
    }

    /** Initialize Firebase and Repository instances */
    private fun initializeDependencies() {
        userRepository = UserRepository.getInstance(this)
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
    }

    /** Bind UI elements and set click listeners */
    private fun initializeUI() {
        photoContainer = findViewById(R.id.photoContainer)
        tvNameAge = findViewById(R.id.tvNameAge)
        tvGender = findViewById(R.id.tvGender)
        tvOrientation = findViewById(R.id.tvOrientation)
        tvBio = findViewById(R.id.tvBio)
        tvPrompts = findViewById(R.id.tvPrompts)
        tvLookingFor = findViewById(R.id.tvLookingFor)
        tvInterests = findViewById(R.id.tvInterestsLifestyle)
        tvEducation = findViewById(R.id.tvPreferences)
        tvLocation = findViewById(R.id.tvLocation)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnSaveContinue = findViewById(R.id.btnSaveContinue)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileSetupActivity::class.java)
            startActivity(intent)
        }

        btnSaveContinue.setOnClickListener {
            val intent = Intent(this, LocationsMapActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /** Setup bottom navigation */
    private fun setupNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> true // Already here
                R.id.nav_matches -> {
                    // Navigate to MatchesActivity
                    // startActivity(Intent(this, MatchesActivity::class.java))
                    true
                }
                R.id.nav_chat -> {
                    // Navigate to ChatActivity
                    // startActivity(Intent(this, ChatActivity::class.java))
                    true
                }
                R.id.nav_settings -> {
                    // Navigate to SettingsActivity
                    // startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    /** Load profile data from local repository and sync with Firebase */
    private fun loadProfileData() {
        val userEmail = auth.currentUser?.email
        if (userEmail == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val user: User? = userRepository.getUserByEmail(userEmail)
                val profile: UserProfile? = userRepository.getProfile(userEmail)

                runOnUiThread {
                    displayUserData(user, profile)
                    loadImages(user)
                }

                // Sync with Firebase in background
                userRepository.syncUserFromFirebase(userEmail)
                userRepository.syncProfileFromFirebase(userEmail)
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@ProfileViewActivity,
                        "Error loading data, showing offline info",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /** Display user and profile info on the UI */
    private fun displayUserData(user: User?, profile: UserProfile?) {
        user?.let {
            tvNameAge.text = "${it.name}, ${it.age ?: ""}"
            tvGender.text = "Gender: ${it.gender ?: "Not specified"}"
            tvOrientation.text = "Orientation: ${it.orientation ?: "Not specified"}"
            tvLocation.text = "Location: ${it.location ?: "Not specified"}"
        }

        profile?.let {
            tvBio.text = it.bio.ifEmpty { "No bio yet" }
            tvPrompts.text = "Life Goal: ${it.prompt1}\nWeekends: ${it.prompt2}\nFriends Say: ${it.prompt3}"
            tvLookingFor.text = "Looking For: ${it.agePreference.ifEmpty { "Not specified" }}"
            tvInterests.text = "Interests: ${it.interests.ifEmpty { "Not specified" }}"
            tvEducation.text = "Education/Occupation: ${it.distancePreference}"
        }

        val isProfileComplete = user != null && profile != null
        btnSaveContinue.isEnabled = isProfileComplete
        btnSaveContinue.alpha = if (isProfileComplete) 1.0f else 0.5f
    }

    /** Load profile and additional images */
    private fun loadImages(user: User?) {
        user?.let {
            // Load main profile image
            user.profileImageUrl?.let { url ->
                if (photoContainer.childCount > 0) {
                    Glide.with(this)
                        .load(url)
                        .into(photoContainer.getChildAt(0) as ImageView)
                }
            }

            // Load additional images
            user.imageUrls?.split(",")?.forEachIndexed { index, url ->
                if (index < photoContainer.childCount - 1 && url.isNotBlank()) {
                    Glide.with(this)
                        .load(url)
                        .into(photoContainer.getChildAt(index + 1) as ImageView)
                }
            }
        }
    }
}
