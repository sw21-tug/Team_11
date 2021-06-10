package com.example.justgo.singleTrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.*
import com.example.justgo.Entitys.*
import com.example.justgo.Food.FoodsActivity
import com.example.justgo.Logic.TripManager
import com.example.justgo.TimeLine.TimeLine
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
        val title = findViewById<TextView>(R.id.trip_title)
        title.text = trip.nameofTrip
        listView = findViewById<ListView>(R.id.feature_list)
    }

    override fun onResume() {
        super.onResume()

        trip = TripManager.getTripbyName(trip.nameofTrip).first()
        tripinfonames = trip.getTripInformationLNameist()
        listView.adapter = TripFeatureAdapter(this, tripinfonames)


        // if you click on "Locations" --> the locations list view and google maps open
        listView.setOnItemClickListener { parent, view, position, id ->
            val element = listView.adapter.getItem(position)

            if(element == "Locations")
            {
                val intent = Intent(this, DestinationsActivity::class.java)
                intent.putExtra("trip",trip)
                startActivity(intent)
            }

            else if (element == "Dates") {
                val intent = Intent(this, TimeLine::class.java)
                intent.putExtra("trip", trip)
                this.startActivity(intent)
            }

            else if (element == "Foods") {
                val intent = Intent(this, FoodsActivity::class.java)
                intent.putExtra("trip", trip)
                this.startActivity(intent)
            }

            else if (element == "Pictures and Videos") {
                val intent = Intent(this, PictureVideoActivity::class.java)
                intent.putExtra("trip", trip)
                this.startActivity(intent)
            }

            else if (element == "Co-Travellers") {
                val intent = Intent(this, CoTravellerActivity::class.java)
                intent.putExtra("trip", trip)
                this.startActivity(intent)
            }

            else if (element == "Costs") {
                val intent = Intent(this, CostsActivity::class.java)
                intent.putExtra("trip", trip)
                this.startActivity(intent)
            }
        }

    }


    fun addItem(view: View) {
        val intent = Intent(this, AddFieldActivity::class.java).apply {}
        intent.putExtra("possible_fields", trip.possibleFields as Serializable)
        startActivityForResult(intent, REQUEST_CODE)
    }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK && data != null) {

                    val result = data.getSerializableExtra("added_field") as String
                    if (result == "Dates"){
                        trip.addTripInformation(TripDates(result))
                    }
                    else if(result == "Pictures and Videos"){
                        trip.addTripInformation(PictureVideoList())
                    }
                    else if(result == "Co-Travellers"){
                        trip.addTripInformation(CoTravellersList())
                    }
                    else if (result == "Locations") {
                        trip.addTripInformation(TripDestination(result))
                    }
                    else if (result == "Foods"){
                        trip.addTripInformation(TripFood(result))
                    }
                    else if (result == "Costs"){
                        trip.addTripInformation(TripCost(result))
                    }
                    else{
                        trip.addTripInformation(TemplateTripinfo(result))
                    }
                    tripinfonames.add(result)
                    trip.possibleFields.remove(result)

                (listView.adapter as TripFeatureAdapter).notifyDataSetChanged()
                TripManager.replaceTrip(
                    TripManager.getTripbyName(trip.nameofTrip).first(),
                    trip
                )
            }
        }
    }
}
