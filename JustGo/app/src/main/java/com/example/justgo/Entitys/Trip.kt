package com.example.justgo.Entitys

import java.io.Serializable

class Trip(name:String,tripType: TripType) : Serializable {
    private var tripID:Int = 0
    var tripInformations: ArrayList<TripInformation> = ArrayList()
    var possibleFields = mutableListOf<String>("Dates", "Locations", "Pictures and Videos", "Transportation", "Accommodation", "Activities", "Foods", "Co-Travellers", "Costs")
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
    fun addID(ID:Int){
        tripID=ID
    }
    fun getID():Int{
        return tripID
    }
}