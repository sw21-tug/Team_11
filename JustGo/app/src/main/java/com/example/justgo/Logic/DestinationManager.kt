package com.example.justgo.Logic

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.justgo.Entitys.Destination
import java.lang.StringBuilder

class DestinationManager {

    companion object{
        val map = hashMapOf<String, ArrayList<Destination>>()
        lateinit var actualOpenTrip: String


        fun changeActualOpenTrip(trip: String){
            actualOpenTrip = trip
        }

        fun addDestination(destination: Destination){
            var bool: Boolean = false
            map.keys.forEach {
                if(it.equals(actualOpenTrip)){
                    bool = true
                }
            }
            if(bool){
                map[actualOpenTrip]?.add(destination)
            }
            else{
                val list: ArrayList<Destination> = ArrayList()
                list.add(destination)
                map.put(actualOpenTrip, list)
            }
        }

        fun getDestinationsForActualTrip() : ArrayList<Destination>?{
            return map.get(actualOpenTrip)
        }

        fun getDestinationFromRESTService(name:String){
            var newDestination : Destination

            var stringbuilder: StringBuilder = StringBuilder()
            stringbuilder.append("https://geocode.xyz/")
            stringbuilder.append(name)
            stringbuilder.append("?json=1")

            val url = stringbuilder.toString()

            println(url)

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                    Response.Listener { response ->
                        var long = response.getDouble("longt")
                        var latt = response.getDouble("latt")
                        newDestination = Destination(name, long, latt)
                        addDestination(newDestination)
                    },
                    Response.ErrorListener { error ->
                        //newDestination = null
                    }
            )
        }
    }
}