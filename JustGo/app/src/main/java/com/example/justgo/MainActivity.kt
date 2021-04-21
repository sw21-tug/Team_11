package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.justgo.Entitys.Trip
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var list_view_of_trips : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arrayAdapter : ArrayAdapter<*>
        TripManager.createSampleTrips();
        list_view_of_trips = findViewById(R.id.list_view_of_trips)

        val trips = mutableListOf("TripObject1\npicture", "TripObject2\npicture", "TripObject3\npicture",
            "TripObject4\npicture", "TripObject5\npicture", "TripObject6\npicture",
            "TripObject7\npicture", "TripObject8\npicture", "TripObject9\npicture",
            "TripObject10\npicture", "TripObject11\npicture", "TripObject12\npicture",
            "TripObject13\npicture", "TripObject14\npicture", "TripObject15\npicture",)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trips)
        list_view_of_trips.adapter = arrayAdapter
        val create_trip : FloatingActionButton
        create_trip = findViewById(R.id.createtripFloatingActionButton)
        create_trip.setOnClickListener {
            val intent = Intent(this,CreateTrip::class.java)
            startActivity(intent)
        }
        val searchTrip: FloatingActionButton
        searchTrip = findViewById(R.id.SearchForTripBt)
        searchTrip.setOnClickListener {

            var tripname:TextView
            var tripstest:ArrayList<Trip>
            tripname=findViewById(R.id.SearchbyNameEditText)
            if(!tripname.text.toString().equals("")){
                tripstest = TripManager.getTripbyName(tripname.text.toString())
                tripstest.forEach {
                    println(it.toString())
                }
            }
        }
    }
}