package com.datingapp.amour

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationsMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var btnBack: ImageButton
    private lateinit var btnLocateMe: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_map)

        btnBack = findViewById(R.id.btnBack)
        btnLocateMe = findViewById(R.id.btnLocateMe)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnBack.setOnClickListener {
            finish()
        }

        btnLocateMe.setOnClickListener {
            if (::googleMap.isInitialized) {
                val myLocation = LatLng(-33.9249, 18.4241)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f))
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val match1 = LatLng(-33.925, 18.423)
        val match2 = LatLng(-33.927, 18.426)
        val match3 = LatLng(-33.922, 18.428)

        googleMap.addMarker(MarkerOptions().position(match1).title("Sophie, 22"))
        googleMap.addMarker(MarkerOptions().position(match2).title("Daniel, 27"))
        googleMap.addMarker(MarkerOptions().position(match3).title("Emily, 23"))

        val cityCenter = LatLng(-33.9249, 18.4241)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCenter, 13f))
    }
}
