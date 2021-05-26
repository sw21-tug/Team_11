package com.example.justgo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.*
import com.example.justgo.Logic.TripManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DestinationsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var destinationListView: ListView
    private lateinit var addDestinationButton: Button
    private lateinit var trip:Trip
    private lateinit var tripDestination: TripDestination
    private val REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        trip = intent.getSerializableExtra("trip") as Trip
        tripDestination = getTripDestinations()
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        destinationListView = findViewById(R.id.destinations_list_view)
        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tripDestination.destinations)
        destinationListView.adapter = arrayAdapter

        addDestinationButton = findViewById(R.id.add_destination_button)
        addDestinationButton.setOnClickListener {
            val intent = Intent(this, AddNewDestination::class.java)
            intent.putExtra("trip",trip)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var lastDestination:Destination? = null
        tripDestination.destinations.forEach {
            val dest= LatLng(it.letit_,it.longit_)
            lastDestination=it
            if(it.letit_ != 0.0 && it.longit_ != 0.0){
                mMap.addMarker(MarkerOptions().position(dest).title(it.name_))
            }
        }
        if(lastDestination!=null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lastDestination!!.letit_,
                lastDestination!!.longit_)))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                var destinationDatabaseHelper = DatabaseHelper(this)
                trip.tripInformations.remove(tripDestination)
                trip.tripInformations.add(destinationDatabaseHelper.viewDestinationbyTrip(trip) as TripInformation)

                tripDestination = getTripDestinations()

                TripManager.replaceTrip(
                        TripManager.getTripbyName(trip.nameofTrip).first(),
                        trip
                )
                val arrayAdapter: ArrayAdapter<*>
                arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tripDestination.destinations)
                destinationListView.adapter = arrayAdapter
            }
        }
    }

    fun getTripDestinations() : TripDestination {
        return (trip.getTripInformationbyName("Locations") as TripDestination)
    }
}