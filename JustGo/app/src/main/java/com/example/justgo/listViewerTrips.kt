package com.example.justgo

import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType
import com.example.justgo.singleTrip.ActivitySingleTrip
import java.io.Serializable


import com.example.justgo.Logic.TripManager

class listViewerTrips() {

    fun startListView(context : Context, layout_id : Int, listview : ListView, trip_type_selected: TripType) {
        val arrayAdapter : ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(context, layout_id, TripManager.getTripsbyType(trip_type_selected))
        listview.adapter = arrayAdapter

        listview.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position) // The item that was clicked
            val intent = Intent(context, ActivitySingleTrip::class.java).apply {}

            intent.putExtra("trip", element as Serializable)
            context.startActivity(intent)
        }
    }
}