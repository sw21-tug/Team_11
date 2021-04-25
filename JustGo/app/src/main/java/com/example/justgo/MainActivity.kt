package com.example.justgo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDate
import com.example.justgo.Entitys.TripType
import com.example.justgo.Logic.TripManager
import com.example.justgo.singleTrip.TripFeatureAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var list_view_of_trips : ListView
    private val SINGLE_TRIP_ACTIVITY = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      
        val layoutid : Int = android.R.layout.simple_list_item_1

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
        val list_view_of_trips = listViewerTrips(this,layoutid, findViewById(R.id.list_view_of_trips), TripManager.getAllTrips())
        list_view_of_trips.startListView()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SINGLE_TRIP_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val result = data.getSerializableExtra("trip") as Trip
                TripManager.replaceTrip(TripManager.getTripbyName(result.nameofTrip).first(), result)
            }
        }
    }
}