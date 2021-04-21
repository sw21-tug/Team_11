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

    private var trip : Trip? = null;
    private val tripinfo = mutableListOf<TripInformation>()
    private val REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_trip)

        initializeTripInfoList();
        trip = intent.getSerializableExtra("trip") as Trip

        var listView = findViewById<ListView>(R.id.feature_list)
        listView.adapter = TripFeatureAdapter(this, trip!!.tripInformations)

    }

    fun addItem(view: View){
        val intent = Intent(this, AddFieldActivity::class.java).apply {}
        intent.putExtra("tripinfolist", tripinfo as Serializable)
        startActivityForResult(intent, REQUEST_CODE)
    }

    fun initializeTripInfoList(){
        var date = TripDate("Date", "")
        var dest = TripDestination("Destination", "")
        tripinfo?.add(date)
        tripinfo?.add(dest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                var result = data.getSerializableExtra("result") as List<TripInformation>
                for(tripinfo : TripInformation in result)
                {
                    if(tripinfo.value != ""){
                        trip?.addTripInformation(tripinfo)
                    }
                }
                findViewById<ListView>(R.id.feature_list).invalidate()
                }
            }
        }
}
