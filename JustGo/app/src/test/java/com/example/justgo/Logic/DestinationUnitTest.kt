package com.example.justgo.Logic

import com.example.justgo.Entitys.Destination
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType
import org.junit.Assert.*
import org.junit.Test

class DestinationUnitTest{

    @Test
    fun addDestinationWorksAsExpected(){
        var trip:Trip = Trip("Barcelona",TripType.shared_ones)
        trip.addDestination(Destination("Barcelona", 20.0, 20.0))

        val arrList: ArrayList<Destination> = ArrayList()
        arrList.add(Destination("Barcelona", 20.0, 20.0))
        val referenceMap = hashMapOf<String, ArrayList<Destination>>()
        referenceMap["Barcelona"] = arrList

        assertEquals(referenceMap.toString(), "{Barcelona="+trip.destinations.toString()+"}");

        trip.clearDestinations()
    }


    @Test
    fun getDestinationsForActualTripWorksAsExpected(){
        var trip:Trip = Trip("Barcelona",TripType.shared_ones)
        trip.addDestination(Destination("Barcelona", 20.0, 20.0))
        val arrList : ArrayList<Destination> = trip.getDestinationsForActualTrip()
        val referenceArrList : ArrayList<Destination> = ArrayList()
        referenceArrList.add(Destination("Barcelona", 20.0, 20.0))
        assertEquals(referenceArrList.toString(), arrList.toString())
        trip.clearDestinations()
    }

    /*@Test
    fun ifNoDestinationsCreatedNoDestinationsYetGetsReturned(){
        val arrList : ArrayList<Destination> = DestinationsRestCallManager.getDestinationsForActualTrip()
        val referenceArrList : ArrayList<Destination> = ArrayList()
        referenceArrList.add(Destination("no destinations yet", 0.0, 0.0))
        assertEquals(referenceArrList.toString(), arrList.toString())
    }*/
}