package com.example.justgo.Logic

import com.example.justgo.Entitys.Destination
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDestination
import com.example.justgo.Entitys.TripType
import org.junit.Assert.assertEquals
import org.junit.Test

class DestinationUnitTest{

    @Test
    fun addDestinationWorksAsExpected() {
        val trip: Trip = Trip("Barcelona", TripType.Sample)
        val destination = TripDestination("Locations")
        destination.destinations.add(Destination("Barcelona", 20.0, 20.0, "Hotel"))
        trip.addTripInformation(destination)

        val arrList: ArrayList<Destination> = ArrayList()
        arrList.add(Destination("Barcelona", 20.0, 20.0, "Hotel"))
        val referenceMap = hashMapOf<String, ArrayList<Destination>>()
        referenceMap["Barcelona"] = arrList

        assertEquals(referenceMap.toString(), "{Barcelona=" + destination.destinations.toString() + "}");
    }

    @Test
    fun getDestinationsForActualTripWorksAsExpected(){
        var trip:Trip = Trip("Barcelona",TripType.Sample)

        val destinations =  TripDestination()
        destinations.destinations.add(Destination("Barcelona", 20.0, 20.0, "Hotel"))
        trip.addTripInformation(destinations)

        val destinationList : MutableList<Destination> = (trip.getTripInformationbyName("Locations") as TripDestination).destinations
        val referenceArrList : MutableList<Destination> = ArrayList()
        referenceArrList.add(Destination("Barcelona", 20.0, 20.0, "Hotel"))
        assertEquals(referenceArrList.toString(), destinationList.toString())
        trip.tripInformations.remove(destinations)
    }

        /*@Test
        fun ifNoDestinationsCreatedNoDestinationsYetGetsReturned(){
            val arrList : ArrayList<Destination> = DestinationsRestCallManager.getDestinationsForActualTrip()
            val referenceArrList : ArrayList<Destination> = ArrayList()
            referenceArrList.add(Destination("no destinations yet", 0.0, 0.0))
            assertEquals(referenceArrList.toString(), arrList.toString())
        }*/
}