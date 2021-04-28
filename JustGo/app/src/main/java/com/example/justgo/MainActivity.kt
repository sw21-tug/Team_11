package com.example.justgo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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

        var current_trip_type: TripType
        current_trip_type = TripType.created_by_others
        val list_view_of_trips = listViewerTrips()
        val list_view_trips_description: TextView
        list_view_trips_description = findViewById(R.id.list_view_description)
        list_view_of_trips.startListView(this, layoutid, findViewById(R.id.list_view_of_trips), current_trip_type)

        val my_trips: Button
        my_trips = findViewById(R.id.my_trips_button)
        my_trips.setOnClickListener {
            list_view_trips_description.text = "My Trips"
            current_trip_type = TripType.self_created
            list_view_of_trips.startListView(this, layoutid, findViewById(R.id.list_view_of_trips), current_trip_type)
        }
        val sample_trips: Button
        sample_trips = findViewById(R.id.sample_trips_button)
        sample_trips.setOnClickListener {
            list_view_trips_description.text = "Sample Trips"
            current_trip_type = TripType.created_by_others
            list_view_of_trips.startListView(this, layoutid, findViewById(R.id.list_view_of_trips), current_trip_type)
        }
        val shared_trips: Button
        shared_trips = findViewById(R.id.shared_trips_button)
        shared_trips.setOnClickListener {
            list_view_trips_description.text = "Shared Trips"
            current_trip_type = TripType.shared_ones
            list_view_of_trips.startListView(this, layoutid, findViewById(R.id.list_view_of_trips), current_trip_type)
        }

        val dropdown_menu: Spinner
        dropdown_menu = findViewById(R.id.sorting_dropdown)
        val array_ = resources.getStringArray(R.array.dropdown_string_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array_)
        dropdown_menu.adapter = adapter
        dropdown_menu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Toast.makeText(this@MainActivity, "${parent?.getItemAtPosition(position).toString()}", Toast.LENGTH_LONG).show()
                if(parent?.getItemAtPosition(position).toString().equals("trip name")){
                    TripManager.sortTripsbyName()
                    list_view_of_trips.startListView(this@MainActivity, layoutid, findViewById(R.id.list_view_of_trips), current_trip_type)
                }

                /*
                mit:  parent?.getItemAtPosition(position).toString()  lesen welches dropdown item ausgewählt ist
                dann
                TripManager.sortTripsbyName() / TripManager.sortTripsbyInput()...
                 */
            }
        }
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