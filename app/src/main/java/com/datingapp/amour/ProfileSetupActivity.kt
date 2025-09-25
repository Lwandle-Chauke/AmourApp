package com.datingapp.amour

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserProfile
import com.datingapp.amour.data.UserRepository
import com.datingapp.amour.utils.FirebaseAuthHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileSetupActivity : AppCompatActivity() {

    // UI components
    private lateinit var btnBack: ImageView
    private lateinit var imgProfile: ImageView
    private lateinit var gridPictures: GridLayout
    private lateinit var etFullName: EditText
    private lateinit var etAge: EditText
    private lateinit var etBio: EditText
    private lateinit var etLifeGoal: EditText
    private lateinit var etWeekend: EditText
    private lateinit var etFriends: EditText
    private lateinit var cbFriends: CheckBox
    private lateinit var cbShortTerm: CheckBox
    private lateinit var cbLongTerm: CheckBox
    private lateinit var rgGender: RadioGroup
    private lateinit var rgOrientation: RadioGroup
    private lateinit var etLocation: EditText
    private lateinit var btnSave: Button

    // Image handling
    private var profileUri: Uri? = null
    private val imageUris = arrayOfNulls<Uri?>(6)
    private var currentImageIndex = -1
    private val PICK_IMAGE_REQUEST = 101

    // Data management
    private lateinit var userRepository: UserRepository
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var userUid: String
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        initializeDependencies()
        getUserInfo()
        initializeViews()
        setupClickListeners()
        loadExistingUserData()
    }

    private fun initializeDependencies() {
        userRepository = UserRepository.getInstance(this)
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    private fun getUserInfo() {
        userUid = intent.getStringExtra("uid") ?: run {
            val currentUser = FirebaseAuthHelper.getCurrentUser()
            if (currentUser != null) {
                currentUser.uid
            } else {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
        }

        userEmail = intent.getStringExtra("email") ?: run {
            val currentUser = FirebaseAuthHelper.getCurrentUser()
            currentUser?.email ?: run {
                Toast.makeText(this, "User email not available", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
        }
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        imgProfile = findViewById(R.id.imgProfile)
        gridPictures = findViewById(R.id.gridPictures)
        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        etBio = findViewById(R.id.etBio)
        etLifeGoal = findViewById(R.id.etLifeGoal)
        etWeekend = findViewById(R.id.etWeekend)
        etFriends = findViewById(R.id.etFriends)
        cbFriends = findViewById(R.id.cbFriends)
        cbShortTerm = findViewById(R.id.cbShortTerm)
        cbLongTerm = findViewById(R.id.cbLongTerm)
        rgGender = findViewById(R.id.rgGender)
        rgOrientation = findViewById(R.id.rgOrientation)
        etLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSaveCore)
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener { onBackPressed() }

        imgProfile.setOnClickListener {
            currentImageIndex = -1
            pickImage()
        }

        for (i in 0 until gridPictures.childCount) {
            val imageView = gridPictures.getChildAt(i) as? ImageView
            imageView?.setOnClickListener {
                currentImageIndex = i
                pickImage()
            }
        }

        btnSave.setOnClickListener { saveProfile() }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            if (selectedImageUri != null) {
                if (currentImageIndex == -1) {
                    profileUri = selectedImageUri
                    imgProfile.setImageURI(profileUri)
                } else {
                    imageUris[currentImageIndex] = selectedImageUri
                    (gridPictures.getChildAt(currentImageIndex) as ImageView).setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun loadExistingUserData() {
        lifecycleScope.launch {
            try {
                val user = userRepository.getUserByUid(userUid)
                user?.let { populateUserForm(it) }

                val profile = userRepository.getUserProfile(userUid)
                profile?.let { populateProfileForm(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun populateUserForm(user: User) {
        etFullName.setText(user.name)
        etAge.setText(user.age?.toString() ?: "")
        etLocation.setText(user.location ?: "")

        when (user.gender) {
            "Male", "He/Him" -> rgGender.check(R.id.rbMale)
            "Female", "She/Her" -> rgGender.check(R.id.rbFemale)
            "Other", "They/Them" -> rgGender.check(R.id.rbThey)
        }

        when (user.orientation) {
            "Straight" -> rgOrientation.check(R.id.rbStraight)
            "Gay" -> rgOrientation.check(R.id.rbGay)
            "Lesbian" -> rgOrientation.check(R.id.rbLesbian)
            "Bisexual" -> rgOrientation.check(R.id.rbBisexual)
            "Asexual" -> rgOrientation.check(R.id.rbAsexual)
            "Other" -> rgOrientation.check(R.id.rbOther)
        }
    }

    private fun populateProfileForm(profile: UserProfile) {
        etBio.setText(profile.bio)
        etLifeGoal.setText(profile.prompt1)
        etWeekend.setText(profile.prompt2)
        etFriends.setText(profile.prompt3)

        val lookingForOptions = profile.lookingFor.split(",")
        cbFriends.isChecked = lookingForOptions.contains("Friends")
        cbShortTerm.isChecked = lookingForOptions.contains("Short-term")
        cbLongTerm.isChecked = lookingForOptions.contains("Long-term")
    }

    private fun saveProfile() {
        val name = etFullName.text.toString().trim()
        val ageText = etAge.text.toString().trim()
        val bio = etBio.text.toString().trim()
        val lifeGoal = etLifeGoal.text.toString().trim()
        val weekend = etWeekend.text.toString().trim()
        val friends = etFriends.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val genderId = rgGender.checkedRadioButtonId
        val orientationId = rgOrientation.checkedRadioButtonId

        if (!validateInput(name, ageText, location, genderId, orientationId, bio, lifeGoal, weekend, friends)) {
            return
        }

        val age = ageText.toInt()
        if (age < 18 || age > 100) {
            etAge.error = "Age must be between 18 and 100"
            etAge.requestFocus()
            return
        }

        val gender = when (genderId) {
            R.id.rbMale -> "Male"
            R.id.rbFemale -> "Female"
            R.id.rbThey -> "Other"
            else -> "Other"
        }

        val orientation = when (orientationId) {
            R.id.rbStraight -> "Straight"
            R.id.rbGay -> "Gay"
            R.id.rbLesbian -> "Lesbian"
            R.id.rbBisexual -> "Bisexual"
            R.id.rbAsexual -> "Asexual"
            R.id.rbOther -> "Other"
            else -> "Other"
        }

        val lookingFor = buildLookingForString()

        setSaveButtonState(false, "Saving...")

        lifecycleScope.launch {
            try {
                val (profileUrl, additionalUrls) = uploadImages()
                val user = createUserObject(name, age, gender, orientation, location, profileUrl, additionalUrls)
                val userProfile = createUserProfileObject(bio, lifeGoal, weekend, friends, lookingFor)

                userRepository.saveUser(user)
                userRepository.saveUserProfile(userProfile)
                userRepository.markProfileComplete(userUid)

                showSuccessAndNavigate()

            } catch (e: Exception) {
                showErrorAndResetButton(e.message ?: "Unknown error")
            }
        }
    }

    private fun validateInput(
        name: String,
        ageText: String,
        location: String,
        genderId: Int,
        orientationId: Int,
        bio: String,
        lifeGoal: String,
        weekend: String,
        friends: String
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
            bio.isEmpty() -> {
                etBio.error = "Please enter your bio"
                etBio.requestFocus()
                false
            }
            lifeGoal.isEmpty() -> {
                etLifeGoal.error = "Please complete this prompt"
                etLifeGoal.requestFocus()
                false
            }
            weekend.isEmpty() -> {
                etWeekend.error = "Please complete this prompt"
                etWeekend.requestFocus()
                false
            }
            friends.isEmpty() -> {
                etFriends.error = "Please complete this prompt"
                etFriends.requestFocus()
                false
            }
            !cbFriends.isChecked && !cbShortTerm.isChecked && !cbLongTerm.isChecked -> {
                Toast.makeText(this, "Please select what you're looking for", Toast.LENGTH_SHORT).show()
                false
            }
            profileUri == null -> {
                Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show()
                false
            }
            imageUris.count { it != null } < 1 -> {
                Toast.makeText(this, "Please select at least 1 additional picture", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun buildLookingForString(): String {
        val options = mutableListOf<String>()
        if (cbFriends.isChecked) options.add("Friends")
        if (cbShortTerm.isChecked) options.add("Short-term")
        if (cbLongTerm.isChecked) options.add("Long-term")
        return options.joinToString(",")
    }

    private suspend fun uploadImages(): Pair<String, String> {
        val profileUrl = uploadImage(profileUri!!, "profile_$userUid")

        val additionalUrls = mutableListOf<String>()
        imageUris.forEachIndexed { index, uri ->
            uri?.let {
                val url = uploadImage(it, "image_${userUid}_$index")
                additionalUrls.add(url)
            }
        }

        return Pair(profileUrl, additionalUrls.joinToString(","))
    }

    private suspend fun uploadImage(uri: Uri, filename: String): String {
        val storageRef = storage.reference.child("images/$filename.jpg")
        val uploadTask = storageRef.putFile(uri).await()
        return storageRef.downloadUrl.await().toString()
    }

    private suspend fun createUserObject(
        name: String,
        age: Int,
        gender: String,
        orientation: String,
        location: String,
        profileUrl: String,
        additionalUrls: String
    ): User {
        val existingUser = userRepository.getUserByUid(userUid)

        return User(
            uid = userUid,
            email = userEmail,
            name = name,
            age = age,
            gender = gender,
            orientation = orientation,
            location = location,
            profileImageUrl = profileUrl,
            imageUrls = if (additionalUrls.isNotEmpty()) additionalUrls else null,
            isProfileComplete = true,
            isEmailVerified = existingUser?.isEmailVerified ?: false,
            createdAt = existingUser?.createdAt ?: System.currentTimeMillis(),
            lastLogin = System.currentTimeMillis()
        )
    }

    private fun createUserProfileObject(
        bio: String,
        lifeGoal: String,
        weekend: String,
        friends: String,
        lookingFor: String
    ): UserProfile {
        return UserProfile(
            uid = userUid,
            email = userEmail,
            bio = bio,
            prompt1 = lifeGoal,
            prompt2 = weekend,
            prompt3 = friends,
            interests = "",
            agePreference = "", // You can add this field later
            distancePreference = "", // You can add this field later
            lookingFor = lookingFor
        )
    }

    private fun setSaveButtonState(enabled: Boolean, text: String) {
        btnSave.isEnabled = enabled
        btnSave.text = text
    }

    private fun showSuccessAndNavigate() {
        runOnUiThread {
            Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()

            // Navigate to LocationsMapActivity instead of MenuActivity
            val intent = Intent(this, LocationsMapActivity::class.java).apply {
                putExtra("uid", userUid)
                putExtra("email", userEmail)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    private fun showErrorAndResetButton(errorMessage: String) {
        runOnUiThread {
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            setSaveButtonState(true, "Save & Continue")
        }
    }

    override fun onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Save Profile?")
            .setMessage("Do you want to save your profile before exiting?")
            .setPositiveButton("Save") { _, _ -> saveProfile() }
            .setNegativeButton("Exit") { _, _ -> super.onBackPressed() }
            .setNeutralButton("Cancel", null)
            .show()
    }
}