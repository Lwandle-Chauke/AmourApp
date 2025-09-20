package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MatchBrowsingActivity : AppCompatActivity() {

    private lateinit var imgMatch: ImageView
    private lateinit var tvMatchNameAge: TextView
    private lateinit var tvMatchBio: TextView
    private lateinit var btnLike: ImageButton
    private lateinit var btnDislike: ImageButton
    private lateinit var btnNotifications: ImageButton
    private lateinit var btnSettings: ImageButton

    private val matches = listOf(
        Match("Alex", 24, "Loves hiking, travel and photography.", R.drawable.ic_person),
        Match("Sophie", 22, "Coffee enthusiast and bookworm.", R.drawable.ic_person),
        Match("Daniel", 27, "Tennis player and music lover.", R.drawable.ic_person)
    )
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_browsing)

        imgMatch = findViewById(R.id.imgMatch)
        tvMatchNameAge = findViewById(R.id.tvMatchNameAge)
        tvMatchBio = findViewById(R.id.tvMatchBio)
        btnLike = findViewById(R.id.btnLike)
        btnDislike = findViewById(R.id.btnDislike)
        btnNotifications = findViewById(R.id.btnNotifications)
        btnSettings = findViewById(R.id.btnSettings)

        showMatch()

        btnLike.setOnClickListener {
            Toast.makeText(this, "You liked ${matches[currentIndex].name}", Toast.LENGTH_SHORT).show()
            nextMatch()
        }

        btnDislike.setOnClickListener {
            Toast.makeText(this, "You skipped ${matches[currentIndex].name}", Toast.LENGTH_SHORT).show()
            nextMatch()
        }

        btnNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationsSettingsActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, NotificationsSettingsActivity::class.java))
        }
    }

    private fun showMatch() {
        val match = matches[currentIndex]
        imgMatch.setImageResource(match.photoResId)
        tvMatchNameAge.text = "${match.name}, ${match.age}"
        tvMatchBio.text = match.bio
    }

    private fun nextMatch() {
        currentIndex++
        if (currentIndex >= matches.size) {
            Toast.makeText(this, "No more matches!", Toast.LENGTH_SHORT).show()
            currentIndex = 0
        }
        showMatch()
    }
}

data class Match(val name: String, val age: Int, val bio: String, val photoResId: Int)
