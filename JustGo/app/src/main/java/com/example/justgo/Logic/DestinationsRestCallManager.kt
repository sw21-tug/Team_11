package com.example.justgo.Logic

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Destination
import com.example.justgo.Entitys.FoodType
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDestination
import com.google.gson.Gson
import java.lang.StringBuilder

class DestinationsRestCallManager {

    companion object {
        fun getDestinationFromRESTService(name: String, accomodation: String, context: Context,trip:Trip) {
            var newDestination: Destination
            val queue = Volley.newRequestQueue(context)
            var stringbuilder: StringBuilder = StringBuilder()
            stringbuilder.append("https://geocode.xyz/")
            stringbuilder.append(name)
            stringbuilder.append("?json=1");
            val url = stringbuilder.toString()
            println(url)

            val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        println(response.toString())
                        var long = response.getDouble("longt")
                        var latt = response.getDouble("latt")
                        newDestination = Destination(name, long, latt, accomodation)
                        var foodDatabaseHelper = DatabaseHelper(context)
                        foodDatabaseHelper.addDestination(newDestination, trip)
                        var tripDestination= trip.getTripInformationbyName("Locations") as TripDestination
                        tripDestination.destinations.add(newDestination)

                    },
                    { error ->
                        print(error.toString())
                    }
            )
            queue.add(jsonObjectRequest)
        }
    }
}