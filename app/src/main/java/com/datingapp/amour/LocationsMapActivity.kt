package com.datingapp.amour

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_map)

        btnBack = findViewById(R.id.btnBack)
        btnLocateMe = findViewById(R.id.btnLocateMe)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnBack.setOnClickListener { finish() }

        btnLocateMe.setOnClickListener {
            if (::googleMap.isInitialized) {
                val myLocation = LatLng(-33.9249, 18.4241)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f))
            }
        }

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        val fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocation != PackageManager.PERMISSION_GRANTED || coarseLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Enable location button only if permission granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
        }

        // Sample markers
        val match1 = LatLng(-33.925, 18.423)
        val match2 = LatLng(-33.927, 18.426)
        val match3 = LatLng(-33.922, 18.428)

        googleMap.addMarker(MarkerOptions().position(match1).title("Sophie, 22"))
        googleMap.addMarker(MarkerOptions().position(match2).title("Daniel, 27"))
        googleMap.addMarker(MarkerOptions().position(match3).title("Emily, 23"))

        val cityCenter = LatLng(-33.9249, 18.4241)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityCenter, 13f))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
                if (::googleMap.isInitialized) {
                    googleMap.isMyLocationEnabled = true
                }
            } else {
                Toast.makeText(this, "Location permission required for maps", Toast.LENGTH_SHORT).show()
            }
        }
    }
}