package com.example.justgo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.justgo.Entitys.Destination
import com.example.justgo.Logic.DestinationManager

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        /*
        var actionbar = supportActionBar
        if(actionbar != null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        */

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // ListView
        destinationListView = findViewById(R.id.destinations_list_view)
        var destinations : ArrayList<Destination> = DestinationManager.getDestinationsForActualTrip()
        var destNames : ArrayList<String> = ArrayList()
        for(i in 0 until destinations.size){
            destNames.add(destinations.get(i).getName())
        }
        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, destNames)
        destinationListView.adapter = arrayAdapter

        // AddButton
        addDestinationButton = findViewById(R.id.add_destination_button)
        addDestinationButton.setOnClickListener {
            val intent = Intent(this, AddNewDestination::class.java)
            startActivity(intent)
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
        

        val destination = DestinationManager.getDestinationsForActualTrip()
        var lastDestination:Destination? = null
        destination?.forEach {
            val dest= LatLng(it.getLetit(),it.getLongit())
            lastDestination=it
            if(it.getLetit() != 0.0 && it.getLongit() != 0.0){
                mMap.addMarker(MarkerOptions().position(dest).title(it.getName()))
            }
        }
        if(lastDestination!=null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lastDestination!!.getLetit(),
                lastDestination!!.getLongit())))
        }
    }
}