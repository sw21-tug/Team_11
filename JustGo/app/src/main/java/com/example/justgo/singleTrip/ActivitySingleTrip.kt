package com.example.justgo.singleTrip

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.DestinationsActivity
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDate
import com.example.justgo.Logic.TripManager
import com.example.justgo.R
import java.io.Serializable

class ActivitySingleTrip : AppCompatActivity() {

    private lateinit var trip : Trip
    private lateinit var tripinfonames:ArrayList<String>
    private val REQUEST_CODE = 0
    private lateinit var listView  : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_trip)

        trip = intent.getSerializableExtra("trip") as Trip

        tripinfonames = trip.getTripInformationLNameist()

        val title = findViewById<TextView>(R.id.trip_title)
        title.text = trip.nameofTrip

        listView = findViewById<ListView>(R.id.feature_list)
        listView.adapter = TripFeatureAdapter(this, tripinfonames)

        // if you click on "Locations" --> the locations list view and google maps open
        val context: Context = this
        listView.setOnItemClickListener { parent, view, position, id ->
            val element = listView.adapter.getItem(position).toString()

            if(element.equals("Locations"))
            {
                val intent = Intent(context, DestinationsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun addItem(view: View){
        val intent = Intent(this, AddFieldActivity::class.java).apply {}
        intent.putExtra("possible_fields", trip.possibleFields as Serializable)
        startActivityForResult(intent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val result = data.getSerializableExtra("added_field") as String
                trip.addTripInformation(TripDate(result, ""))
                tripinfonames.add(result)
                trip.possibleFields.remove(result)

                (listView.adapter as TripFeatureAdapter).notifyDataSetChanged()
                TripManager.replaceTrip(TripManager.getTripbyName(trip.nameofTrip).first(), trip)
                }
            }
        }
}
