package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDate
import com.example.justgo.Entitys.TripDestination
import com.example.justgo.Entitys.TripInformation
import com.example.justgo.singleTrip.AddFieldActivity
import com.example.justgo.singleTrip.TripFeatureAdapter
import java.io.Serializable

class ActivitySingleTrip : AppCompatActivity() {

    private lateinit var trip : Trip
    private val tripinfo = mutableListOf<TripInformation>()
    private val REQUEST_CODE = 0
    private lateinit var listView  : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_trip)

        initializeTripInfoList()
        trip = intent.getSerializableExtra("trip") as Trip

        listView = findViewById<ListView>(R.id.feature_list)
        listView.adapter = TripFeatureAdapter(this, trip.tripInformations)

    }

    fun addItem(view: View){
        val intent = Intent(this, AddFieldActivity::class.java).apply {}
        intent.putExtra("tripinfolist", tripinfo as Serializable)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun initializeTripInfoList(){
        val date = TripDate("Date", "")
        val dest = TripDestination("Destination", "")
        tripinfo.add(date)
        tripinfo.add(dest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val result = data.getSerializableExtra("result") as List<TripInformation>
                for(tripinfo : TripInformation in result)
                {
                    if(tripinfo.value != ""){
                        trip.addTripInformation(tripinfo)
                    }
                }
                listView.invalidate()
                }
            }
        }
}
