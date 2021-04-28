package com.example.justgo.Entitys

import java.io.Serializable
import java.lang.StringBuilder

class Trip(name:String,tripType: TripType) : Serializable {

    var tripInformations: ArrayList<TripInformation> = ArrayList()
    var nameofTrip:String = name
    var tripType:TripType = tripType

    fun addTripInformation(tripInformation:TripInformation){
        this.tripInformations.add(tripInformation)
    }

    fun getTripInformationbyName(name:String):TripInformation?{
        tripInformations.forEach {
            if(it.name == name){
                return it
            }
        }
        return null
    }

    fun getTripInformationLNameist(): ArrayList<String>{
        var tripInfoNames: ArrayList<String> = ArrayList()
        tripInformations.forEach{
            tripInfoNames.add(it.name)
        }
        return tripInfoNames
    }

    override fun toString(): String {
        return "$nameofTrip"
    }


}