package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType
import com.example.justgo.Logic.ListViewerTrips
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var list_view_of_trips : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      
        val layoutid : Int = android.R.layout.simple_list_item_1

        val list_view_of_trips = ListViewerTrips(this, layoutid, findViewById(R.id.list_view_of_trips), TripManager.getTripsbyType(TripType.self_created))
        list_view_of_trips.startListView()

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
            var trips:ArrayList<Trip>
            tripname=findViewById(R.id.SearchbyNameEditText)
            if(!tripname.text.toString().equals("")){
                trips = TripManager.getTripbyName(tripname.text.toString())
                trips.forEach {
                    println(it.toString())
                }
                list_view_of_trips.changeTripsList(trips)
                list_view_of_trips.startListView()
            }
        }
        val myTrips: Button
        myTrips = findViewById(R.id.my_trips_btn)
        myTrips.setOnClickListener {
            var trips:ArrayList<Trip>
            trips = TripManager.getTripsbyType(TripType.self_created)
            trips.forEach {
                println(it.toString())
            }
            list_view_of_trips.changeTripsList(trips)
            list_view_of_trips.startListView()
            myTrips.isClickable = false
            val sampleTrips: Button
            sampleTrips = findViewById(R.id.sample_trips_btn)
            sampleTrips.isClickable = true
            val sharedTrips: Button
            sharedTrips = findViewById(R.id.shared_trips_btn)
            sharedTrips.isClickable = true
        }

        val sampleTrips: Button
        sampleTrips = findViewById(R.id.sample_trips_btn)
        sampleTrips.setOnClickListener {
            var trips:ArrayList<Trip>
            trips = TripManager.getTripsbyType(TripType.created_by_others)
            trips.forEach {
                println(it.toString())
            }
            list_view_of_trips.changeTripsList(trips)
            list_view_of_trips.startListView()
            sampleTrips.isClickable = false
            val myTrips: Button
            myTrips = findViewById(R.id.my_trips_btn)
            myTrips.isClickable = true
            val sharedTrips: Button
            sharedTrips = findViewById(R.id.shared_trips_btn)
            sharedTrips.isClickable = true
        }

        val sharedTrips: Button
        sharedTrips = findViewById(R.id.shared_trips_btn)
        sharedTrips.setOnClickListener {
            var trips:ArrayList<Trip>
            trips = TripManager.getTripsbyType(TripType.shared_ones)
            trips.forEach {
                println(it.toString())
            }
            list_view_of_trips.changeTripsList(trips)
            list_view_of_trips.startListView()
            sharedTrips.isClickable = false
            val sampleTrips: Button
            sampleTrips = findViewById(R.id.sample_trips_btn)
            sampleTrips.isClickable = true
            val myTrips: Button
            myTrips = findViewById(R.id.my_trips_btn)
            myTrips.isClickable = true
        }
    }
}