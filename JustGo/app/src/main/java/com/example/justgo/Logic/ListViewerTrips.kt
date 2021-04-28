package com.example.justgo.Logic

import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.justgo.Entitys.TripType
import com.example.justgo.singleTrip.ActivitySingleTrip
import java.io.Serializable

class ListViewerTrips(var context : Context, var layout_id : Int, var listview : ListView, var trip_list : List<Any>) {

 /*   fun startListView(context : Context, layout_id : Int, listview : ListView, trip_type_selected: TripType) {
        val arrayAdapter : ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(context, layout_id, TripManager.getTripsbyType(trip_type_selected))
        listview.adapter = arrayAdapter

        listview.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position) // The item that was clicked
            val intent = Intent(context, ActivitySingleTrip::class.java).apply {}

            intent.putExtra("trip", element as Serializable)
            context.startActivity(intent)
        }
    }*/

    private var layout_id1 : Int = layout_id
    private var list_view_of_trips : ListView = listview
        get() = field
    private var trips : List<Any> = trip_list



    fun startListView() {
        val arrayAdapter: ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(context, layout_id1, trips)
        list_view_of_trips.adapter = arrayAdapter

        listview.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position) // The item that was clicked
            val intent = Intent(context, ActivitySingleTrip::class.java).apply {}

            intent.putExtra("trip", element as Serializable)
            context.startActivity(intent)
        }
    }

    fun changeTripsList(trip_list: List<Any>) {
        this.trips = trip_list
    }
}