package com.example.justgo.Entitys

import junit.framework.Assert.assertEquals
import org.junit.Test

class TripTest{
    @Test
    fun addTripPictures_updatesTripInformation(){
        val trip = Trip("test", tripType = TripType.SelfCreated)

        val pictureList = PictureVideoList()
        trip.addTripInformation(pictureList)

        val addedInfo = trip.getTripInformationbyName("Pictures and Videos")
        assertEquals(addedInfo, pictureList)
    }

    @Test
    fun addTripCoTravellers_updatesTripInformation(){
        val trip = Trip("test", tripType = TripType.SelfCreated)

        val coTraveller = CoTravellersList()
        trip.addTripInformation(coTraveller)

        val addedInfo = trip.getTripInformationbyName("Co-Travellers")
        assertEquals(addedInfo, coTraveller)
    }

    @Test
    fun addTripFood_updatesTripInformation(){
        val trip = Trip("test", tripType = TripType.SelfCreated)

        val tripFood = TripFood("Foods")
        trip.addTripInformation(tripFood)

        val addedInfo = trip.getTripInformationbyName("Foods")
        assertEquals(addedInfo, tripFood)
    }


    @Test
    fun addTripLocation_updatesTripInformation(){
        val trip = Trip("test", tripType = TripType.SelfCreated)

        val tripDest = TripDestination("Locations")
        trip.addTripInformation(tripDest)

        val addedInfo = trip.getTripInformationbyName("Locations")
        assertEquals(addedInfo, tripDest)
    }
}