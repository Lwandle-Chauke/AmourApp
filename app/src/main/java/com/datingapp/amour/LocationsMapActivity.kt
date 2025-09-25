package com.datingapp.amour

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
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

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var currentLocation: Location? = null
    private var isLocationPermissionGranted = false
    private var userUid: String = ""
    private var userEmail: String = ""

    private val handler = Handler(Looper.getMainLooper())
    private val locationFoundRunnable = Runnable { navigateToMenu() }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val DEFAULT_ZOOM_LEVEL = 14f
        private const val LOCATION_DELAY_MS = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_map)

        userUid = intent.getStringExtra("uid") ?: ""
        userEmail = intent.getStringExtra("email") ?: ""

        btnBack = findViewById(R.id.btnBack)
        btnLocateMe = findViewById(R.id.btnLocateMe)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()
        createLocationCallback()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btnBack.setOnClickListener { finish() }
        btnLocateMe.setOnClickListener {
            if (isLocationPermissionGranted) getCurrentLocation() else checkLocationPermission()
        }

        checkLocationPermission()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    currentLocation = location
                    updateMapWithCurrentLocation()
                    handler.removeCallbacks(locationFoundRunnable)
                    handler.postDelayed(locationFoundRunnable, LOCATION_DELAY_MS)
                    fusedLocationClient.removeLocationUpdates(this)
                }
            }
        }
    }

    private fun checkLocationPermission() {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        isLocationPermissionGranted = fineLocationGranted || coarseLocationGranted

        if (!isLocationPermissionGranted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (!isLocationPermissionGranted) {
            checkLocationPermission()
            return
        }

        Toast.makeText(this, "Locating...", Toast.LENGTH_SHORT).show()
        btnLocateMe.isEnabled = false

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                updateMapWithCurrentLocation()
                handler.removeCallbacks(locationFoundRunnable)
                handler.postDelayed(locationFoundRunnable, LOCATION_DELAY_MS)
            } else {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to get location: ${e.message}", Toast.LENGTH_SHORT).show()
            btnLocateMe.isEnabled = true
            showDefaultLocation()
        }
    }

    private fun updateMapWithCurrentLocation() {
        currentLocation?.let { location ->
            val currentLatLng = LatLng(location.latitude, location.longitude)
            if (::googleMap.isInitialized) {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM_LEVEL)
                )
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Your Location"))
                addSampleMatchesAround(currentLatLng)

                btnLocateMe.isEnabled = true
                Toast.makeText(this, "Location detected! Navigating...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDefaultLocation() {
        val defaultLocation = LatLng(-33.9249, 18.4241)
        if (::googleMap.isInitialized) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM_LEVEL))
            addSampleMatchesAround(defaultLocation)
            handler.removeCallbacks(locationFoundRunnable)
            handler.postDelayed(locationFoundRunnable, LOCATION_DELAY_MS)
        }
    }

    private fun addSampleMatchesAround(center: LatLng) {
        val match1 = LatLng(center.latitude + 0.002, center.longitude + 0.001)
        val match2 = LatLng(center.latitude - 0.001, center.longitude + 0.003)
        val match3 = LatLng(center.latitude + 0.003, center.longitude - 0.002)

        googleMap.addMarker(MarkerOptions().position(match1).title("Sophie, 22"))
        googleMap.addMarker(MarkerOptions().position(match2).title("Daniel, 27"))
        googleMap.addMarker(MarkerOptions().position(match3).title("Emily, 23"))
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isLocationPermissionGranted && ::googleMap.isInitialized) {
            googleMap.isMyLocationEnabled = true
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        enableMyLocation()
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        getCurrentLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            isLocationPermissionGranted = grantResults.isNotEmpty() &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            (grantResults.size > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED))
            if (isLocationPermissionGranted) {
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
                enableMyLocation()
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Location permission denied. Using default location.", Toast.LENGTH_LONG).show()
                showDefaultLocation()
            }
        }
    }

    private fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("uid", userUid)
        intent.putExtra("email", userEmail)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        if (isLocationPermissionGranted && ::googleMap.isInitialized) getCurrentLocation()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(locationFoundRunnable)
        if (::fusedLocationClient.isInitialized) fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(locationFoundRunnable)
    }
}
