package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.datingapp.amour.data.UserRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository
    private lateinit var userUid: String

    // UI components
    private lateinit var photoContainer: LinearLayout
    private lateinit var tvNameAge: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvOrientation: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvPrompts: TextView
    private lateinit var tvLookingFor: TextView
    private lateinit var tvInterestsLifestyle: TextView
    private lateinit var tvPreferences: TextView
    private lateinit var tvLocation: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnSaveContinue: Button
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)

        try {
            initializeDependencies()
            getUserUid()
            initializeViews()
            setupClickListeners()
            setupNavigation()
            loadProfileData()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initializeDependencies() {
        userRepository = UserRepository.getInstance(this)
    }

    private fun getUserUid() {
        userUid = intent.getStringExtra("uid") ?: run {
            Toast.makeText(this, "User UID not available", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }

    private fun initializeViews() {
        try {
            photoContainer = findViewById(R.id.photoContainer)
            tvNameAge = findViewById(R.id.tvNameAge)
            tvGender = findViewById(R.id.tvGender)
            tvOrientation = findViewById(R.id.tvOrientation)
            tvBio = findViewById(R.id.tvBio)
            tvPrompts = findViewById(R.id.tvPrompts)
            tvLookingFor = findViewById(R.id.tvLookingFor)
            tvInterestsLifestyle = findViewById(R.id.tvInterestsLifestyle)
            tvPreferences = findViewById(R.id.tvPreferences)
            tvLocation = findViewById(R.id.tvLocation)
            btnEditProfile = findViewById(R.id.btnEditProfile)
            btnSaveContinue = findViewById(R.id.btnSaveContinue)
            bottomNavigation = findViewById(R.id.bottomNavigation)
        } catch (e: Exception) {
            Toast.makeText(this, "Layout error: ${e.message}", Toast.LENGTH_LONG).show()
            throw e
        }
    }

    private fun setupClickListeners() {
        btnEditProfile.setOnClickListener {
            val intent = Intent(this, ProfileSetupActivity::class.java)
            intent.putExtra("uid", userUid)
            startActivity(intent)
        }

        btnSaveContinue.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.putExtra("uid", userUid)
            startActivity(intent)
            finish()
        }
    }

    private fun setupNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_profile -> true
                R.id.nav_matches -> {
                    Toast.makeText(this, "Matches feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_chat -> {
                    Toast.makeText(this, "Chat feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Settings feature coming soon", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        bottomNavigation.selectedItemId = R.id.nav_profile
    }

    private fun loadProfileData() {
        lifecycleScope.launch {
            try {
                val (user, profile) = withContext(Dispatchers.IO) {
                    Pair(
                        userRepository.getUserByUid(userUid),
                        userRepository.getUserProfile(userUid)
                    )
                }

                runOnUiThread {
                    displayUserData(user, profile)
                    loadImages(user)
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@ProfileViewActivity,
                        "Error loading profile: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun displayUserData(user: com.datingapp.amour.data.User?, profile: com.datingapp.amour.data.UserProfile?) {
        user?.let {
            tvNameAge.text = "${it.name ?: "No name"}, ${it.age ?: "N/A"}"
            tvGender.text = "Gender: ${it.gender ?: "Not specified"}"
            tvOrientation.text = "Orientation: ${it.orientation ?: "Not specified"}"
            tvLocation.text = "Location: ${it.location ?: "Not specified"}"
        }

        profile?.let {
            tvBio.text = it.bio.ifEmpty { "No bio yet" }
            tvPrompts.text = buildPromptsText(it)
            tvLookingFor.text = "Looking For: ${it.lookingFor.ifEmpty { "Not specified" }}"
            tvInterestsLifestyle.text = "Interests: ${it.interests.ifEmpty { "Not specified" }}"
            tvPreferences.text = "Preferences: ${it.distancePreference.ifEmpty { "Not specified" }}"
        }

        val isProfileComplete = user != null && profile != null
        btnSaveContinue.isEnabled = isProfileComplete
        btnSaveContinue.alpha = if (isProfileComplete) 1.0f else 0.5f
    }

    private fun buildPromptsText(profile: com.datingapp.amour.data.UserProfile): String {
        return StringBuilder().apply {
            append("Life Goal: ${profile.prompt1.ifEmpty { "Not specified" }}\n")
            append("Weekends: ${profile.prompt2.ifEmpty { "Not specified" }}\n")
            append("Friends Say: ${profile.prompt3.ifEmpty { "Not specified" }}")
        }.toString()
    }

    private fun loadImages(user: com.datingapp.amour.data.User?) {
        user?.let {
            it.profileImageUrl?.let { url ->
                if (photoContainer.childCount > 0) {
                    val firstImageView = photoContainer.getChildAt(0) as? ImageView
                    firstImageView?.let { imageView ->
                        Glide.with(this)
                            .load(url)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(imageView)
                    }
                }
            }

            it.imageUrls?.split(",")?.forEachIndexed { index, url ->
                if (index + 1 < photoContainer.childCount && url.isNotBlank()) {
                    val imageView = photoContainer.getChildAt(index + 1) as? ImageView
                    imageView?.let { iv ->
                        Glide.with(this)
                            .load(url)
                            .placeholder(android.R.drawable.ic_menu_gallery)
                            .into(iv)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadProfileData()
    }
}