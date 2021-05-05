package com.example.justgo.Logic

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.justgo.Entitys.Destination
import com.google.gson.Gson
import java.lang.StringBuilder

class DestinationManager {

    companion object {
        val map = hashMapOf<String, ArrayList<Destination>>()
        var actualOpenTrip: String = String()

        fun clearDestinations() {
            map.clear()
        }

        fun changeActualOpenTrip(trip: String) {
            actualOpenTrip = trip
        }

        fun addDestination(destination: Destination) {
            var bool: Boolean = false
            map.keys.forEach {
                if (it.equals(actualOpenTrip)) {
                    bool = true
                }
            }
            if (bool) {
                map[actualOpenTrip]?.add(destination)
            } else {
                val list: ArrayList<Destination> = ArrayList()
                list.add(destination)
                map.put(actualOpenTrip, list)
            }
        }

        fun getDestinationsForActualTrip(): ArrayList<Destination> {

            var retListNullable: ArrayList<Destination>? = map.get(actualOpenTrip)
            var retList: ArrayList<Destination> = ArrayList()
            if (retListNullable.isNullOrEmpty()) {
                val dest = Destination("no destinations yet", 0.0, 0.0)
                retList.add(dest)
            } else {
                retList = retListNullable
            }
            return retList
        }

        fun getDestinationFromRESTService(name: String, context: Context) {
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
                        newDestination = Destination(name, long, latt)
                        DestinationManager.addDestination(newDestination)

                    },
                    { error ->
                        print(error.toString())
                    }
            )
            queue.add(jsonObjectRequest)
        }
    }
}