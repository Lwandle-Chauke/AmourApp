package com.datingapp.amour

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class NotificationsSettingsActivity : AppCompatActivity() {

    private lateinit var lvNotifications: ListView
    private lateinit var switchNotifications: SwitchCompat
    private lateinit var switchLocation: SwitchCompat
    private lateinit var btnEditProfile: Button
    private lateinit var btnLogout: Button
    private lateinit var tabNotifications: LinearLayout
    private lateinit var tabSettings: ScrollView
    private lateinit var btnTabNotifications: TextView
    private lateinit var btnTabSettings: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications_settings)

        // Find views
        tabNotifications = findViewById(R.id.tabNotifications)
        tabSettings = findViewById(R.id.tabSettings)
        btnTabNotifications = findViewById(R.id.btnTabNotifications)
        btnTabSettings = findViewById(R.id.btnTabSettings)

        lvNotifications = findViewById(R.id.lvNotifications)
        switchNotifications = findViewById(R.id.switchNotifications)
        switchLocation = findViewById(R.id.switchLocation)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnLogout = findViewById(R.id.btnLogout)

        // Setup notifications list
        val demoNotifications = listOf(
            "Sophie liked your profile",
            "Daniel sent you a message",
            "New match: Emily",
            "Alex viewed your profile"
        )
        lvNotifications.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, demoNotifications)

        // Switch listeners
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Notifications enabled" else "Notifications disabled", Toast.LENGTH_SHORT).show()
        }

        switchLocation.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if (isChecked) "Location sharing enabled" else "Location sharing disabled", Toast.LENGTH_SHORT).show()
        }

        // Buttons
        btnEditProfile.setOnClickListener {
            startActivity(Intent(this, ProfileSetupActivity::class.java))
        }

        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WelcomeSignupActivity::class.java))
            finish()
        }

        // Tab switching functions
        fun showNotificationsTab() {
            tabNotifications.visibility = View.VISIBLE
            tabSettings.visibility = View.GONE
        }

        fun showSettingsTab() {
            tabNotifications.visibility = View.GONE
            tabSettings.visibility = View.VISIBLE
        }

        // Initial tab
        showNotificationsTab()

        // Tab click listeners
        btnTabNotifications.setOnClickListener { showNotificationsTab() }
        btnTabSettings.setOnClickListener { showSettingsTab() }
    }
}
