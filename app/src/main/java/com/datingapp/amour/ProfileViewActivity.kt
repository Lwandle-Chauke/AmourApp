package com.datingapp.amour

// ------------------- IMPORTS -------------------
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.datingapp.amour.data.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewActivity : AppCompatActivity() {

    private lateinit var photoContainer: LinearLayout
    private lateinit var tvNameAge: TextView
    private lateinit var tvGender: TextView
    private lateinit var tvOrientation: TextView
    private lateinit var tvBio: TextView
    private lateinit var tvPrompts: TextView
    private lateinit var tvLookingFor: TextView
    private lateinit var tvPreferences: TextView
    private lateinit var tvInterestsLifestyle: TextView
    private lateinit var tvLocation: TextView
    private lateinit var btnEditProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_view)

        // Initialize UI
        photoContainer = findViewById(R.id.photoContainer)
        tvNameAge = findViewById(R.id.tvNameAge)
        tvGender = findViewById(R.id.tvGender)
        tvOrientation = findViewById(R.id.tvOrientation)
        tvBio = findViewById(R.id.tvBio)
        tvPrompts = findViewById(R.id.tvPrompts)
        tvLookingFor = findViewById(R.id.tvLookingFor)
        tvPreferences = findViewById(R.id.tvPreferences)
        tvInterestsLifestyle = findViewById(R.id.tvInterestsLifestyle)
        tvLocation = findViewById(R.id.tvLocation)
        btnEditProfile = findViewById(R.id.btnEditProfile)

        loadProfile()
    }

    private fun loadProfile() {
        val auth = FirebaseAuth.getInstance()
        val email = auth.currentUser?.email ?: "temp@example.com"

        // ----------------- LOAD FROM ROOM -----------------
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(this@ProfileViewActivity)
            val profile = db.userProfileDao().getProfile(email)
            val user = db.userDao().getUserByEmail(email)

            profile?.let {
                runOnUiThread {
                    tvNameAge.text = "${user?.name ?: "User"}, ${it.agePreference}"
                    tvBio.text = it.bio
                    tvPrompts.text = "${it.prompt1}\n${it.prompt2}\n${it.prompt3}"
                    tvInterestsLifestyle.text = it.interests
                    tvPreferences.text = "Age Pref: ${it.agePreference}, Distance: ${it.distancePreference}"
                }
            }
        }

        // ----------------- LOAD PROFILE IMAGE -----------------
        val storageRef = FirebaseStorage.getInstance().reference.child("profile_photos/${auth.currentUser?.uid}.jpg")
        Glide.with(this).load(storageRef).into(photoContainer.getChildAt(0) as ImageView)
    }
}
