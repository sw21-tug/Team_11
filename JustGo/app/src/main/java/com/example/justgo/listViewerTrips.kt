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


class listViewerTrips(var context : Context, var layout_id : Int, var listview : ListView, var trip_list : List<Any>) {

    private var layout_id1 : Int = layout_id
    private var list_view_of_trips : ListView = listview
        get() = field
    private var trips : List<Any> = trip_list



    fun startListView() {
        val arrayAdapter : ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(context, layout_id1, trips)
        list_view_of_trips.adapter = arrayAdapter

        list_view_of_trips.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position) // The item that was clicked
            val intent = Intent(context, ActivitySingleTrip::class.java).apply {}

            intent.putExtra("trip", element as Serializable)
            context.startActivity(intent)
        }
    }

}