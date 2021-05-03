package com.example.justgo.Logic

import com.example.justgo.Entitys.Destination
import com.google.common.collect.Maps
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class DestinationUnitTest{

    @Test
    fun addDestinationWorksAsExpected(){
        DestinationManager.clearDestinations()
        DestinationManager.changeActualOpenTrip("Barcelona")
        DestinationManager.addDestination(Destination("Barcelona", 20.0, 20.0))

        val arrList: ArrayList<Destination> = ArrayList()
        arrList.add(Destination("Barcelona", 20.0, 20.0))
        val referenceMap = hashMapOf<String, ArrayList<Destination>>()
        referenceMap["Barcelona"] = arrList

        assertEquals(referenceMap.toString(), DestinationManager.map.toString());

        DestinationManager.clearDestinations()
    }

    @Test
    fun changeActualOpenTripWorksAsExpected(){
        DestinationManager.changeActualOpenTrip("Barcelona")
        assertEquals("Barcelona", DestinationManager.actualOpenTrip)
    }

    @Test
    fun getDestinationsForActualTripWorksAsExpected(){
        DestinationManager.changeActualOpenTrip("Barcelona")
        DestinationManager.addDestination(Destination("Barcelona", 20.0, 20.0))
        val arrList : ArrayList<Destination> = DestinationManager.getDestinationsForActualTrip()
        val referenceArrList : ArrayList<Destination> = ArrayList()
        referenceArrList.add(Destination("Barcelona", 20.0, 20.0))
        assertEquals(referenceArrList.toString(), arrList.toString())
    }

    @Test
    fun ifNoDestinationsCreatedNoDestinationsYetGetsReturned(){
        val arrList : ArrayList<Destination> = DestinationManager.getDestinationsForActualTrip()
        val referenceArrList : ArrayList<Destination> = ArrayList()
        referenceArrList.add(Destination("no destinations yet", 0.0, 0.0))
        assertEquals(referenceArrList.toString(), arrList.toString())
    }
}