package com.example.justgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var list_view_of_trips : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayAdapter : ArrayAdapter<*>
        list_view_of_trips = findViewById(R.id.list_view_of_trips)

        val trips = mutableListOf("TripObject1\npicture", "TripObject2\npicture", "TripObject3\npicture",
            "TripObject4\npicture", "TripObject5\npicture", "TripObject6\npicture",
            "TripObject7\npicture", "TripObject8\npicture", "TripObject9\npicture",
            "TripObject10\npicture", "TripObject11\npicture", "TripObject12\npicture",
            "TripObject13\npicture", "TripObject14\npicture", "TripObject15\npicture",)

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trips)
        list_view_of_trips.adapter = arrayAdapter
    }
}