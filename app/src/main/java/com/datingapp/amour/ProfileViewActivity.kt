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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Main profile display activity
 * Shows complete user profile with options to edit and navigate to other app sections
 */
class ProfileViewActivity : AppCompatActivity() {

    // Data management
    private lateinit var userRepository: UserRepository
    private lateinit var userEmail: String

    // UI component declarations
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

        // Initialize components in order
        initializeDependencies()
        getUserEmail()
        initializeViews()
        setupClickListeners()
        setupNavigation()
        loadProfileData()
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
    }

    /**
     * Setup button click listeners
     */
    private fun setupClickListeners() {
        // Edit profile button - navigate to profile setup
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileSetupActivity::class.java)
            intent.putExtra("email", userEmail)
            startActivity(intent)
        }

        // Save & continue button - navigate to next app section
        btnSaveContinue.setOnClickListener {
            val intent = Intent(this, LocationsMapActivity::class.java)
            startActivity(intent)
            // Don't finish - allow back navigation
        }
    }

    /**
     * Setup bottom navigation menu
     */
    private fun setupNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> {
                    // Already on profile page
                    true
                }
                R.id.nav_matches -> {
                    // Navigate to matches page
                    Toast.makeText(this, "Matches feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_chat -> {
                    // Navigate to chat page
                    Toast.makeText(this, "Chat feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    // Navigate to settings page
                    Toast.makeText(this, "Settings feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Highlight current page
        bottomNavigation.selectedItemId = R.id.nav_profile
    }

    /**
     * Load user profile data from database and sync with Firebase
     */
    private fun loadProfileData() {
        lifecycleScope.launch {
            try {
                // Load from local database first for quick display
                val (user, profile) = withContext(Dispatchers.IO) {
                    Pair(
                        userRepository.getUserByEmail(userEmail),
                        userRepository.getProfile(userEmail)
                    )
                }

                // Display loaded data
                runOnUiThread {
                    displayUserData(user, profile)
                    loadImages(user)
                }

                // Sync with Firebase in background
                withContext(Dispatchers.IO) {
                    userRepository.syncUserFromFirebase(userEmail)
                    userRepository.syncProfileFromFirebase(userEmail)

                    // Reload data after sync
                    val syncedUser = userRepository.getUserByEmail(userEmail)
                    val syncedProfile = userRepository.getProfile(userEmail)

                    runOnUiThread {
                        displayUserData(syncedUser, syncedProfile)
                        loadImages(syncedUser)
                    }
                }

            } catch (e: Exception) {
                // Handle errors gracefully
                runOnUiThread {
                    Toast.makeText(
                        this@ProfileViewActivity,
                        "Error loading data: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /**
     * Display user data in UI components
     */
    private fun displayUserData(user: User?, profile: UserProfile?) {
        // Display basic user information
        user?.let {
            tvNameAge.text = "${it.name}, ${it.age ?: "N/A"}"
            tvGender.text = "Gender: ${it.gender ?: "Not specified"}"
            tvOrientation.text = "Orientation: ${it.orientation ?: "Not specified"}"
            tvLocation.text = "Location: ${it.location ?: "Not specified"}"
        }

        // Display profile details
        profile?.let {
            tvBio.text = it.bio.ifEmpty { "No bio yet" }
            tvPrompts.text = buildPromptsText(it)
            tvLookingFor.text = "Looking For: ${it.agePreference.ifEmpty { "Not specified" }}"
            tvInterests.text = "Interests: ${it.interests.ifEmpty { "Not specified" }}"
            tvEducation.text = "Education/Occupation: ${it.distancePreference}"
        }

        // Enable continue button only if profile is complete
        val isProfileComplete = user != null && profile != null
        btnSaveContinue.isEnabled = isProfileComplete
        btnSaveContinue.alpha = if (isProfileComplete) 1.0f else 0.5f
    }

    /**
     * Build formatted text for profile prompts
     */
    private fun buildPromptsText(profile: UserProfile): String {
        return StringBuilder().apply {
            append("Life Goal: ${profile.prompt1}\n")
            append("Weekends: ${profile.prompt2}\n")
            append("Friends Say: ${profile.prompt3}")
        }.toString()
    }

    /**
     * Load profile images using Glide library
     */
    private fun loadImages(user: User?) {
        user?.let {
            // Load main profile image
            it.profileImageUrl?.let { url ->
                if (photoContainer.childCount > 0) {
                    Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.ic_profile_placeholder) // Add placeholder in drawable
                        .into(photoContainer.getChildAt(0) as ImageView)
                }
            }

            // Load additional images
            it.imageUrls?.split(",")?.forEachIndexed { index, url ->
                if (index < photoContainer.childCount - 1 && url.isNotBlank()) {
                    Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.ic_image_placeholder) // Add placeholder in drawable
                        .into(photoContainer.getChildAt(index + 1) as ImageView)
                }
            }
        }
    }

    /**
     * Refresh data when activity resumes
     */
    override fun onResume() {
        super.onResume()
        loadProfileData() // Reload data when returning from edit screen
    }
}