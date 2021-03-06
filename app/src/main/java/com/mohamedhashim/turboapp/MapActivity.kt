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
import com.google.firebase.database.*


class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var mapView: MapView? = null
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    private lateinit var database: DatabaseReference
    private lateinit var ref: DatabaseReference
    private var TAG = "LOGS_FIREBASE"

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

        database = FirebaseDatabase.getInstance().reference
        ref = database.child("bins")
//        writeNewUser("1","Vodafone",30.073521799999998,31.0183642,50.0f)
//        writeNewUser("2","IBM Egypt",30.076902,31.025026,50.0f)
//        writeNewUser("3","Microsoft",30.071118,31.016743,50.0f)
//        writeNewUser("4","Valeo",30.078768,31.017816,50.0f)
//        writeNewUser("5","Raya",30.074312,31.019999,50.0f)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val phoneQuery = ref
        phoneQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (singleSnapshot in dataSnapshot.children) {
                    val bin = singleSnapshot.getValue<Bin>(Bin::class.java)
                    addMarker(bin!!, googleMap)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("logs_", "onCancelled", databaseError.toException())
            }
        })
        val camBuilder = CameraPosition.builder()
        camBuilder.bearing(45f)
        camBuilder.tilt(30f)
        camBuilder.target(LatLng(30.073521799999998, 31.0183642))
        camBuilder.zoom(15f)
        val cp: CameraPosition = camBuilder.build()
        googleMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(cp))
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

    private fun writeNewBin(id: String, name: String, lat: Double?, lng: Double?, status: Float?) {
        val bin = Bin(id, name, lat, lng, status)
        ref.child(id).setValue(bin)
    }

    private fun addMarker(bin: Bin, googleMap: GoogleMap?) {
        val location = LatLng(bin.lat!!, bin.lng!!)
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bin))
            .title(bin.name + " " + bin.status + "%")

        googleMap!!.addMarker(markerOptions)
    }
}
