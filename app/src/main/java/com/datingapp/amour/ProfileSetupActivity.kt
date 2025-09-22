package com.datingapp.amour

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.datingapp.amour.data.AppDatabase
import com.datingapp.amour.data.UserProfile
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class ProfileSetupActivity : AppCompatActivity() {

    private lateinit var imgProfile: ImageView
    private lateinit var tvNameAge: TextView
    private lateinit var photosContainer: LinearLayout
    private lateinit var etBio: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etPrompt1: EditText
    private lateinit var etPrompt2: EditText
    private lateinit var etPrompt3: EditText
    private lateinit var etInterests: EditText
    private lateinit var etAgePreference: EditText
    private lateinit var etDistancePreference: EditText
    private lateinit var btnContinue: Button

    private lateinit var db: AppDatabase
    private val firebaseDB = FirebaseDatabase.getInstance().reference
    private val storage = FirebaseStorage.getInstance().reference

    private var email: String? = null
    private var name: String? = null
    private var age: String? = null

    private val photoUris = mutableListOf<Uri?>()
    private val PICK_IMAGE_REQUEST = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        // Bind views
        imgProfile = findViewById(R.id.imgProfile)
        tvNameAge = findViewById(R.id.tvNameAge)
        photosContainer = findViewById(R.id.photosContainer)
        etBio = findViewById(R.id.etBio)
        rgGender = findViewById(R.id.rgGender)
        etPrompt1 = findViewById(R.id.etPrompt1)
        etPrompt2 = findViewById(R.id.etPrompt2)
        etPrompt3 = findViewById(R.id.etPrompt3)
        etInterests = findViewById(R.id.etInterests)
        etAgePreference = findViewById(R.id.etAgePreference)
        etDistancePreference = findViewById(R.id.etDistancePreference)
        btnContinue = findViewById(R.id.btnContinue)

        db = AppDatabase.getDatabase(this)

        // Get data passed from signup/login
        email = intent.getStringExtra("email")
        name = intent.getStringExtra("name")
        age = intent.getStringExtra("age")

        // Show user name & age at the top
        tvNameAge.text = if (!name.isNullOrEmpty() && !age.isNullOrEmpty()) "$name, $age" else "Your Name, Age"

        // Profile photo click (main photo)
        imgProfile.setOnClickListener { pickPhoto(0) }

        // Initialize 6 photo slots
        initPhotoSlots(6)

        // Load existing profile if available
        loadProfile()

        // Save profile button
        btnContinue.setOnClickListener { saveProfile() }
    }

    private fun initPhotoSlots(count: Int) {
        for (i in 0 until count) {
            val img = ImageView(this)
            img.layoutParams = LinearLayout.LayoutParams(0, 250, 1f)
            img.setImageResource(android.R.drawable.ic_menu_camera)
            img.setBackgroundColor(0xFFE0E0E0.toInt())
            img.setPadding(4, 4, 4, 4)
            img.setOnClickListener { pickPhoto(i) }
            photosContainer.addView(img)
            photoUris.add(null)
        }
    }

    private fun pickPhoto(slot: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST + slot)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data?.data != null) {
            val slot = requestCode - PICK_IMAGE_REQUEST
            val uri = data.data!!
            photoUris[slot] = uri
            val targetImg = if (slot == 0) imgProfile else photosContainer.getChildAt(slot) as ImageView
            Glide.with(this).load(uri).into(targetImg)
        }
    }

    private fun saveProfile() {
        val bio = etBio.text.toString().trim()
        val genderId = rgGender.checkedRadioButtonId
        val prompt1 = etPrompt1.text.toString().trim()
        val prompt2 = etPrompt2.text.toString().trim()
        val prompt3 = etPrompt3.text.toString().trim()
        val interests = etInterests.text.toString().trim()
        val agePref = etAgePreference.text.toString().trim()
        val distancePref = etDistancePreference.text.toString().trim()

        if (bio.isEmpty()) { etBio.error = "Enter bio"; return }
        if (genderId == -1) { Toast.makeText(this,"Select gender",Toast.LENGTH_SHORT).show(); return }
        if (prompt1.isEmpty() || prompt2.isEmpty() || prompt3.isEmpty()) {
            Toast.makeText(this,"Answer all prompts",Toast.LENGTH_SHORT).show(); return
        }
        if (interests.isEmpty()) { etInterests.error="Add interests"; return }

        val gender = findViewById<RadioButton>(genderId).text.toString()

        email?.let { userEmail ->
            val safeEmail = userEmail.replace(".", "_")

            // Upload photos to Firebase Storage + Save URLs
            photoUris.forEachIndexed { index, uri ->
                uri?.let {
                    val photoRef = storage.child("profiles/$safeEmail/photo_$index.jpg")
                    photoRef.putFile(it)
                        .addOnSuccessListener {
                            photoRef.downloadUrl.addOnSuccessListener { url ->
                                firebaseDB.child("profiles").child(safeEmail)
                                    .child("photoUrls").child(index.toString())
                                    .setValue(url.toString())
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            // Save profile to Firebase Realtime DB
            val profileMap = mapOf(
                "bio" to bio,
                "gender" to gender,
                "prompts" to listOf(prompt1,prompt2,prompt3),
                "interests" to interests,
                "agePreference" to agePref,
                "distancePreference" to distancePref
            )
            firebaseDB.child("profiles").child(safeEmail).updateChildren(profileMap)

            // Save offline with Room
            val profile = UserProfile(
                email = userEmail,
                bio = bio,
                gender = gender,
                prompt1 = prompt1,
                prompt2 = prompt2,
                prompt3 = prompt3,
                interests = interests,
                agePreference = agePref,
                distancePreference = distancePref
            )
            lifecycleScope.launch { db.userProfileDao().insert(profile) }

            Toast.makeText(this,"Profile saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProfile() {
        email?.let { userEmail ->
            val safeEmail = userEmail.replace(".", "_")

            // Load Firebase data
            firebaseDB.child("profiles").child(safeEmail).get().addOnSuccessListener { snapshot ->
                snapshot.value?.let { data ->
                    val map = data as Map<*, *>
                    etBio.setText(map["bio"] as? String ?: "")
                    val prompts = map["prompts"] as? List<*> ?: emptyList<Any>()
                    if (prompts.size >= 3) {
                        etPrompt1.setText(prompts[0] as? String)
                        etPrompt2.setText(prompts[1] as? String)
                        etPrompt3.setText(prompts[2] as? String)
                    }
                    etInterests.setText(map["interests"] as? String ?: "")
                    etAgePreference.setText(map["agePreference"] as? String ?: "")
                    etDistancePreference.setText(map["distancePreference"] as? String ?: "")
                }
            }

            // Load profile photos (from URLs)
            firebaseDB.child("profiles").child(safeEmail).child("photoUrls")
                .get().addOnSuccessListener { snapshot ->
                    snapshot.children.forEach { child ->
                        val index = child.key?.toIntOrNull()
                        val url = child.getValue(String::class.java)
                        if (index != null && url != null) {
                            val targetImg = if (index == 0) imgProfile else photosContainer.getChildAt(index) as ImageView
                            Glide.with(this).load(url).into(targetImg)
                        }
                    }
                }
        }
    }
}
