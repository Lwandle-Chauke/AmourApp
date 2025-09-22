package com.datingapp.amour

// ------------------- IMPORTS -------------------
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileSetupActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var btnBack: ImageView
    private lateinit var imgProfile: ImageView
    private lateinit var etFullName: EditText
    private lateinit var etAge: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rgOrientation: RadioGroup
    private lateinit var etLocation: EditText
    private lateinit var btnSave: Button

    private var profileUri: Uri? = null  // Profile picture URI
    private val PICK_IMAGE_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Initialize UI
        btnBack = findViewById(R.id.btnBack)
        imgProfile = findViewById(R.id.imgProfile)
        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        rgGender = findViewById(R.id.rgGender)
        rgOrientation = findViewById(R.id.rgOrientation)
        etLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSaveCore)

        // Back button
        btnBack.setOnClickListener { finish() }

        // Image picker
        imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Save button
        btnSave.setOnClickListener { saveProfile() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            profileUri = data?.data
            imgProfile.setImageURI(profileUri)
        }
    }

    private fun saveProfile() {
        val name = etFullName.text.toString().trim()
        val age = etAge.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val genderId = rgGender.checkedRadioButtonId
        val orientationId = rgOrientation.checkedRadioButtonId

        if (name.isEmpty() || age.isEmpty() || genderId == -1 || orientationId == -1) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val gender = findViewById<RadioButton>(genderId).text.toString()
        val orientation = findViewById<RadioButton>(orientationId).text.toString()

        // --------------- SAVE TO ROOM -----------------
        val db = AppDatabase.getDatabase(this)
        val user = User(name = name)
        CoroutineScope(Dispatchers.IO).launch {
            db.userDao().insert(user)
        }

        // --------------- SAVE TO FIREBASE -------------
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDb = FirebaseDatabase.getInstance().reference
        val userId = firebaseAuth.currentUser?.uid ?: "temp_user"
        val userMap = mapOf(
            "name" to name,
            "age" to age,
            "gender" to gender,
            "orientation" to orientation,
            "location" to location
        )

        firebaseDb.child("users").child(userId).setValue(userMap)

        // Upload profile picture if selected
        profileUri?.let { uri ->
            val storageRef = FirebaseStorage.getInstance().reference.child("profile_photos/$userId.jpg")
            storageRef.putFile(uri)
        }

        // Go to Interests Activity
        startActivity(Intent(this, ActivityInterests::class.java))
    }
}
