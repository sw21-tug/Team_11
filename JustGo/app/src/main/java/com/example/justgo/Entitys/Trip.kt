package com.example.justgo.Entitys

import java.lang.StringBuilder

class Trip(name:String,tripType: TripType){
    //props
    private var tripInformations: ArrayList<TripInformation> = ArrayList()
    var nameofTrip:String

    var tripType:TripType

    //Constructor

    init {
        this.nameofTrip = name
        this.tripType=tripType;
    }

    //Methods

    fun addTripInformation(tripInformation:TripInformation){
        this.tripInformations.add(tripInformation)
    }

    fun getTripInformationbyName(name:String):TripInformation?{

        tripInformations.forEach {
            if(it.name.equals(name)){
                return it
            }
        }
        return null
    }

    override fun toString(): String {
        return "$nameofTrip"
    }


}