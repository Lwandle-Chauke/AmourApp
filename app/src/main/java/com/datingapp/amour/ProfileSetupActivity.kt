package com.datingapp.amour

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Activity for initial profile setup
 * Users provide basic information, select photos, and set core profile details
 */
class ProfileSetupActivity : AppCompatActivity() {

    // UI component declarations
    private lateinit var btnBack: ImageView
    private lateinit var imgProfile: ImageView
    private lateinit var gridPictures: GridLayout
    private lateinit var etFullName: EditText
    private lateinit var etAge: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rgOrientation: RadioGroup
    private lateinit var etLocation: EditText
    private lateinit var btnSave: Button

    // Image handling variables
    private var profileUri: Uri? = null
    private val imageUris = arrayOfNulls<Uri?>(6) // Array for additional images
    private var currentImageIndex = -1 // -1 for profile image, 0-5 for additional images
    private val PICK_IMAGE_REQUEST = 101 // Request code for image picker

    // Data management
    private lateinit var userRepository: UserRepository
    private lateinit var storage: FirebaseStorage
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Initialize data management components
        initializeDependencies()

        // Get user email from intent
        getUserEmail()

        // Initialize UI components
        initializeViews()

        // Setup click listeners
        setupClickListeners()

        // Load existing user data if available
        loadExistingUserData()
    }

    /**
     * Initialize repositories and Firebase services
     */
    private fun initializeDependencies() {
        userRepository = UserRepository.getInstance(this)
        storage = FirebaseStorage.getInstance()
    }

    /**
     * Extract user email from intent or finish activity if not available
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
        btnBack = findViewById(R.id.btnBack)
        imgProfile = findViewById(R.id.imgProfile)
        gridPictures = findViewById(R.id.gridPictures)
        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        rgGender = findViewById(R.id.rgGender)
        rgOrientation = findViewById(R.id.rgOrientation)
        etLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSaveCore)
    }

    /**
     * Setup click listeners for buttons and image views
     */
    private fun setupClickListeners() {
        // Back button - return to previous screen
        btnBack.setOnClickListener { onBackPressed() }

        // Profile image click - open image picker
        imgProfile.setOnClickListener {
            currentImageIndex = -1 // Mark as profile image
            pickImage()
        }

        // Additional image clicks - open image picker for specific slot
        for (i in 0 until gridPictures.childCount) {
            val imageView = gridPictures.getChildAt(i) as? ImageView
            imageView?.setOnClickListener {
                currentImageIndex = i // Mark which additional image slot
                pickImage()
            }
        }

        // Save button - validate and save profile
        btnSave.setOnClickListener { saveProfile() }
    }

    /**
     * Launch image picker intent
     */
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*" // Only show image files
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    /**
     * Handle result from image picker
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            if (selectedImageUri != null) {
                if (currentImageIndex == -1) {
                    // Profile image selected
                    profileUri = selectedImageUri
                    imgProfile.setImageURI(profileUri)
                } else {
                    // Additional image selected
                    imageUris[currentImageIndex] = selectedImageUri
                    (gridPictures.getChildAt(currentImageIndex) as ImageView).setImageURI(selectedImageUri)
                }
            }
        }
    }

    /**
     * Load existing user data from database
     */
    private fun loadExistingUserData() {
        lifecycleScope.launch {
            try {
                val user = userRepository.getUserByEmail(userEmail)
                user?.let { populateForm(it) }
            } catch (e: Exception) {
                // Silent fail - it's okay if no existing data
            }
        }
    }

    /**
     * Populate form fields with existing user data
     */
    private fun populateForm(user: User) {
        etFullName.setText(user.name)
        etAge.setText(user.age?.toString() ?: "")
        etLocation.setText(user.location ?: "")

        // Set gender radio button
        when (user.gender) {
            "He/Him" -> rgGender.check(R.id.rbMale)
            "She/Her" -> rgGender.check(R.id.rbFemale)
            "They/Them" -> rgGender.check(R.id.rbThey)
        }

        // Set orientation radio button
        when (user.orientation) {
            "Straight" -> rgOrientation.check(R.id.rbStraight)
            "Gay" -> rgOrientation.check(R.id.rbGay)
            "Lesbian" -> rgOrientation.check(R.id.rbLesbian)
            "Bisexual" -> rgOrientation.check(R.id.rbBisexual)
            "Asexual" -> rgOrientation.check(R.id.rbAsexual)
            "Other" -> rgOrientation.check(R.id.rbOther)
        }
    }

    /**
     * Validate and save profile data to local and online databases
     */
    private fun saveProfile() {
        // Get input values
        val name = etFullName.text.toString().trim()
        val ageText = etAge.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val genderId = rgGender.checkedRadioButtonId
        val orientationId = rgOrientation.checkedRadioButtonId

        // Validate all required fields
        if (!validateInput(name, ageText, location, genderId, orientationId)) {
            return
        }

        // Parse age and validate range
        val age = ageText.toInt()
        if (age < 18 || age > 100) {
            etAge.error = "Age must be between 18 and 100"
            etAge.requestFocus()
            return
        }

        // Get selected gender and orientation
        val gender = findViewById<RadioButton>(genderId).text.toString()
        val orientation = findViewById<RadioButton>(orientationId).text.toString()

        // Disable save button during processing
        setSaveButtonState(false, "Saving...")

        // Save profile in background
        lifecycleScope.launch {
            try {
                // Upload images and get URLs
                val (profileUrl, additionalUrls) = uploadImages()

                // Create or update user object
                val user = createUserObject(name, age, gender, orientation, location, profileUrl, additionalUrls)

                // Save to databases
                userRepository.saveUser(user)

                // Success - navigate to next activity
                showSuccessAndNavigate()

            } catch (e: Exception) {
                // Handle errors
                showErrorAndResetButton(e.message ?: "Unknown error")
            }
        }
    }

    /**
     * Validate all input fields
     */
    private fun validateInput(
        name: String,
        ageText: String,
        location: String,
        genderId: Int,
        orientationId: Int
    ): Boolean {
        return when {
            name.isEmpty() -> {
                etFullName.error = "Please enter your full name"
                etFullName.requestFocus()
                false
            }
            ageText.isEmpty() -> {
                etAge.error = "Please enter your age"
                etAge.requestFocus()
                false
            }
            ageText.toIntOrNull() == null -> {
                etAge.error = "Please enter a valid age"
                etAge.requestFocus()
                false
            }
            location.isEmpty() -> {
                etLocation.error = "Please enter your location"
                etLocation.requestFocus()
                false
            }
            genderId == -1 -> {
                Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
                false
            }
            orientationId == -1 -> {
                Toast.makeText(this, "Please select your orientation", Toast.LENGTH_SHORT).show()
                false
            }
            profileUri == null -> {
                Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    /**
     * Upload profile and additional images to Firebase Storage
     */
    private suspend fun uploadImages(): Pair<String, String> {
        // Upload profile image
        val profileUrl = uploadImage(
            profileUri!!,
            "profile_${userEmail.replace(".", "_")}"
        )

        // Upload additional images
        val additionalUrls = mutableListOf<String>()
        imageUris.forEachIndexed { index, uri ->
            uri?.let {
                val url = uploadImage(it, "image_${userEmail.replace(".", "_")}_$index")
                additionalUrls.add(url)
            }
        }

        return Pair(profileUrl, additionalUrls.joinToString(","))
    }

    /**
     * Upload single image to Firebase Storage
     */
    private suspend fun uploadImage(uri: Uri, filename: String): String {
        val storageRef = storage.reference.child("images/$filename.jpg")
        storageRef.putFile(uri).await() // Wait for upload to complete
        return storageRef.downloadUrl.await().toString() // Get download URL
    }

    /**
     * Create User object with all collected data
     */
    private suspend fun createUserObject(
        name: String,
        age: Int,
        gender: String,
        orientation: String,
        location: String,
        profileUrl: String,
        additionalUrls: String
    ): User {
        // Get existing user to preserve password
        val existingUser = userRepository.getUserByEmail(userEmail)

        return User(
            email = userEmail,
            name = name,
            passwordHash = existingUser?.passwordHash ?: "", // Preserve existing password
            age = age,
            gender = gender,
            orientation = orientation,
            location = location,
            profileImageUrl = profileUrl,
            imageUrls = if (additionalUrls.isNotEmpty()) additionalUrls else null
        )
    }

    /**
     * Update save button state during processing
     */
    private fun setSaveButtonState(enabled: Boolean, text: String) {
        btnSave.isEnabled = enabled
        btnSave.text = text
    }

    /**
     * Handle successful save operation
     */
    private fun showSuccessAndNavigate() {
        runOnUiThread {
            Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, InterestsActivity::class.java)
            intent.putExtra("email", userEmail)
            startActivity(intent)
            // Don't finish - allow back navigation
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