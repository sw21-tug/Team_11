package com.example.justgo

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class listViewerTrips(var context : Context, var layout_id : Int, var listview : ListView, var trip_list : List<Any>) {

    private var layout_id1 : Int = layout_id
    private var list_view_of_trips : ListView = listview
        get() = field
    private var trips : List<Any> = trip_list



    fun startListView() {
        val arrayAdapter : ArrayAdapter<*>
        arrayAdapter = ArrayAdapter(context, layout_id1, trips)
        list_view_of_trips.adapter = arrayAdapter
    }

}