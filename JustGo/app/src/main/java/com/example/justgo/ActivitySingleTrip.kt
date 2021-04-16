package com.example.justgo

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Entitys.Trip
import com.example.justgo.singleTrip.TripFeatureAdapter

class ActivitySingleTrip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_trip)

        val trip = intent.getSerializableExtra("trip") as Trip

        var listView = findViewById<ListView>(R.id.feature_list)
        listView.adapter = TripFeatureAdapter(this, trip.tripInformations)

    }
}