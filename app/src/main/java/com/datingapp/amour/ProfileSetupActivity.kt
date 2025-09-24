package com.datingapp.amour

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.datingapp.amour.data.User
import com.datingapp.amour.data.UserRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileSetupActivity : AppCompatActivity() {

    // UI
    private lateinit var btnBack: ImageView
    private lateinit var imgProfile: ImageView
    private lateinit var gridPictures: GridLayout
    private lateinit var etFullName: EditText
    private lateinit var etAge: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rgOrientation: RadioGroup
    private lateinit var etLocation: EditText
    private lateinit var btnSave: Button

    // Image handling
    private var profileUri: Uri? = null
    private val imageUris = arrayOfNulls<Uri?>(6)
    private var currentImageIndex = -1
    private val PICK_IMAGE_REQUEST = 101

    // Repository / Firebase
    private lateinit var userRepository: UserRepository
    private lateinit var storage: FirebaseStorage
    private lateinit var userEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Initialize dependencies
        userRepository = UserRepository.getInstance(this)
        storage = FirebaseStorage.getInstance()

        // Get email
        userEmail = intent.getStringExtra("email") ?: run {
            Toast.makeText(this, "User email not available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize UI
        btnBack = findViewById(R.id.btnBack)
        imgProfile = findViewById(R.id.imgProfile)
        gridPictures = findViewById(R.id.gridPictures)
        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        rgGender = findViewById(R.id.rgGender)
        rgOrientation = findViewById(R.id.rgOrientation)
        etLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSaveCore)

        btnBack.setOnClickListener { finish() }

        // Profile picture picker
        imgProfile.setOnClickListener {
            currentImageIndex = -1
            pickImageFromGallery()
        }

        // Grid picture pickers
        for (i in 0 until gridPictures.childCount) {
            val child = gridPictures.getChildAt(i)
            if (child is ImageView) {
                val index = i
                child.setOnClickListener {
                    currentImageIndex = index
                    pickImageFromGallery()
                }
            }
        }

        btnSave.setOnClickListener { saveProfile() }

        // Load existing user data for editing
        loadExistingUser()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (currentImageIndex == -1) {
                profileUri = uri
                imgProfile.setImageURI(profileUri)
            } else {
                imageUris[currentImageIndex] = uri
                val imageView = gridPictures.getChildAt(currentImageIndex) as ImageView
                imageView.setImageURI(uri)
            }
        }
    }

    private fun loadExistingUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getUserByEmail(userEmail)
            user?.let { u ->
                runOnUiThread {
                    etFullName.setText(u.name)
                    etAge.setText(u.age.toString())
                    etLocation.setText(u.location)

                    // Gender
                    when (u.gender) {
                        "He/Him" -> rgGender.check(R.id.rbMale)
                        "She/Her" -> rgGender.check(R.id.rbFemale)
                        "They/Them" -> rgGender.check(R.id.rbThey)
                    }

                    // Orientation
                    when (u.orientation) {
                        "Straight" -> rgOrientation.check(R.id.rbStraight)
                        "Gay" -> rgOrientation.check(R.id.rbGay)
                        "Lesbian" -> rgOrientation.check(R.id.rbLesbian)
                        "Bisexual" -> rgOrientation.check(R.id.rbBisexual)
                        "Asexual" -> rgOrientation.check(R.id.rbAsexual)
                        "Other" -> rgOrientation.check(R.id.rbOther)
                    }

                    // TODO: Load images using Glide/Picasso if URLs exist
                    // u.profileImageUrl?.let { url -> Glide.with(this).load(url).into(imgProfile) }
                    // u.imageUrls?.split(",")?.forEachIndexed { index, url -> ... }
                }
            }
        }
    }

    private fun saveProfile() {
        val name = etFullName.text.toString().trim()
        val ageText = etAge.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val genderId = rgGender.checkedRadioButtonId
        val orientationId = rgOrientation.checkedRadioButtonId

        if (name.isEmpty() || ageText.isEmpty() || genderId == -1 || orientationId == -1) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val age = ageText.toIntOrNull()
        if (age == null || age < 18 || age > 100) {
            Toast.makeText(this, "Please enter a valid age (18-100)", Toast.LENGTH_SHORT).show()
            return
        }

        if (profileUri == null) {
            Toast.makeText(this, "Please select a profile picture", Toast.LENGTH_SHORT).show()
            return
        }

        btnSave.isEnabled = false
        btnSave.text = "Saving..."

        val gender = findViewById<RadioButton>(genderId).text.toString()
        val orientation = findViewById<RadioButton>(orientationId).text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val profileUrl = uploadImage(profileUri!!, "profile_${userEmail.replace(".", "_")}")

                val additionalUrls = mutableListOf<String>()
                imageUris.forEachIndexed { index, uri ->
                    uri?.let {
                        val url = uploadImage(it, "image_${userEmail.replace(".", "_")}_$index")
                        additionalUrls.add(url)
                    }
                }

                val imageUrlsString = additionalUrls.joinToString(",")

                val user = User(
                    email = userEmail,
                    name = name,
                    phone = null,
                    passwordHash = null,
                    age = age,
                    gender = gender,
                    orientation = orientation,
                    location = location,
                    profileImageUrl = profileUrl,
                    imageUrls = imageUrlsString
                )

                userRepository.saveUser(user)

                runOnUiThread {
                    Toast.makeText(this@ProfileSetupActivity, "Profile saved!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ProfileSetupActivity, InterestsActivity::class.java)
                    intent.putExtra("email", userEmail)
                    startActivity(intent)
                    finish()
                }

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@ProfileSetupActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    btnSave.isEnabled = true
                    btnSave.text = "Save & Continue"
                }
            }
        }
    }

    private suspend fun uploadImage(uri: Uri, filename: String): String {
        val storageRef = storage.reference.child("images/$filename.jpg")
        storageRef.putFile(uri).await()
        return storageRef.downloadUrl.await().toString()
    }
}
