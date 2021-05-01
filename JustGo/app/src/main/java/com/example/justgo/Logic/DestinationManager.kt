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

    companion object{
        val map = hashMapOf<String, ArrayList<Destination>>()
        var actualOpenTrip: String = String()


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

        fun getDestinationsForActualTrip() : ArrayList<Destination>{

            var retListNullable: ArrayList<Destination>? = map.get(actualOpenTrip)
            var retList: ArrayList<Destination> = ArrayList()
            if(retListNullable.isNullOrEmpty())
            {
                val dest = Destination("no destinations yet", 0.0, 0.0)
                retList.add(dest)
            }
            else
            {
                retList = retListNullable
            }
            return retList
        }

        fun getDestinationFromRESTService(name:String,context:Context){
            var newDestination : Destination
            val queue = Volley.newRequestQueue(context)
            /*var stringbuilder: StringBuilder = StringBuilder()
            stringbuilder.append("http://open.mapquestapi.com/geocoding/v1/address?key=Wv4PRCfN0XmBAW6y4PqBG8XHzHtPAb1S&location=")
            stringbuilder.append(name)

            val url = stringbuilder.toString()*/
            var url = "http://ip.jsontest.com/"

            println(url)


            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                    Response.Listener { response ->
                        println(response.toString())
                        println()
                        println()
                        println()
                        println("Hier")
                        /*var resultJsonArray = response.getJSONArray(2)
                        var loactionJsonArray = resultJsonArray.getJSONArray(1)
                        var locationObject = loactionJsonArray.getJSONObject(0)
                        var latlng=locationObject.getJSONObject("latLng")


                        var long = latlng.getDouble("longt")
                        var latt = latlng.getDouble("latt")
                        newDestination = Destination(name, long, latt)
                        addDestination(newDestination)*/
                    },
                    Response.ErrorListener { error ->
                        //newDestination = null
                        print(error.toString())
                    }
            )
            queue.add(jsonObjectRequest)
        }
    }
}