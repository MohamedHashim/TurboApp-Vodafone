package com.mohamedhashim.turboapp

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var mapView: MapView? = null
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        mapView!!.onSaveInstanceState(mapViewBundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initMapView(savedInstanceState)
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment!!.getMapAsync(this)

        //        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("bins")
//
        myRef.setValue("Hello, World!")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("logs_", "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("logs", "Failed to read value.", error.toException())
            }
        })

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val location = LatLng(30.073521799999998, 31.0183642)
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bin))
            .title("Smart Village")

        googleMap!!.addMarker(markerOptions)
        val camBuilder = CameraPosition.builder()
        camBuilder.bearing(45f)
        camBuilder.tilt(30f)
        camBuilder.target(location)
        camBuilder.zoom(15f)
        val cp: CameraPosition = camBuilder.build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp))
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null)
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        mapView = findViewById(R.id.mapView)
        mapView!!.onCreate(mapViewBundle)
        mapView!!.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        if (mapView != null)
            mapView!!.onResume()
    }

    override fun onStart() {
        super.onStart()
        if (mapView != null)
            mapView!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        if (mapView != null)
            mapView!!.onStop()
    }

    override fun onPause() {
        super.onPause()
        if (mapView != null)
            mapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null)
            mapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }
}
