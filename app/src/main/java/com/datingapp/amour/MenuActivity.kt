package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Profile edit
        val editProfile: TextView = findViewById(R.id.editProfile)
        editProfile.setOnClickListener {
            startActivity(Intent(this, ProfileViewActivity::class.java))
        }

        // Matches
        findViewById<LinearLayout>(R.id.optionMatches).setOnClickListener {
            startActivity(Intent(this, MatchBrowsingActivity::class.java))
        }

        // Messages
        findViewById<LinearLayout>(R.id.optionMessages).setOnClickListener {
            startActivity(Intent(this, MessagingActivity::class.java))
        }

        // Notifications
        findViewById<LinearLayout>(R.id.optionNotifications).setOnClickListener {
            startActivity(Intent(this, NotificationsSettingsActivity::class.java))
        }

        // Location
        findViewById<LinearLayout>(R.id.optionLocation).setOnClickListener {
            startActivity(Intent(this, LocationsMapActivity::class.java))
        }


        // Logout
        findViewById<LinearLayout>(R.id.optionLogout).setOnClickListener {
            // TODO: Clear session/authentication
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Bottom Navigation
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.nav_settings
        bottomNav.setOnItemSelectedListener { item ->            when (item.itemId) {
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileViewActivity::class.java))
                    true
                }
                R.id.nav_matches -> {
                    startActivity(Intent(this, MatchBrowsingActivity::class.java))
                    true
                }
                R.id.nav_chat -> {
                    startActivity(Intent(this, MessagingActivity::class.java))
                    true
                }
                R.id.nav_notifications -> {
                    startActivity(Intent(this, NotificationsSettingsActivity::class.java))
                    true
                }
                R.id.nav_location -> {
                    startActivity(Intent(this, LocationsMapActivity::class.java))
                    true
                }
                R.id.nav_settings -> true
                else -> false
            }
        }
    }
}
