package com.example.justgo

import com.example.justgo.Entitys.Trip
//import com.example.justgo.Entitys.TripDate
import com.example.justgo.Entitys.TripDestination
import com.example.justgo.Entitys.TripType
import com.example.justgo.Logic.TripManager
import org.junit.Assert.assertEquals
import org.junit.Test

class TripUnitTest {
   @Test
   fun createTrip() {
       TripManager.clearTrips()
       TripManager.createTrip("New York", TripType.self_created)
       val result: Trip = TripManager.getAllTrips().first()
       assertEquals(result.toString(), "New York")
       TripManager.clearTrips()
   }

    @Test
    fun getTripByName() {
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()

        TripManager.createTrip("New York", TripType.self_created)
        TripManager.createTrip("New York", TripType.self_created)
        TripManager.createTrip("Barcelona", TripType.self_created)

        trips = TripManager.getTripbyName("New York")

        trips.forEach {
            assertEquals(it.toString(), "New York")
        }
        TripManager.clearTrips()
    }

    @Test
    fun getTripsbyType() {
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()

        TripManager.createTrip("New York", TripType.self_created)
        TripManager.createTrip("New York", TripType.self_created)
        TripManager.createTrip("Barcelona", TripType.shared_ones)

        trips = TripManager.getTripsbyType(TripType.self_created)

        trips.forEach {
            assertEquals(it.toString(), "New York")
        }
        TripManager.clearTrips()
    }

    @Test
    fun getAllTrips() {
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()
        TripManager.createTrip("New York", TripType.self_created)
        TripManager.createTrip("New York", TripType.self_created)
        TripManager.createTrip("Barcelona", TripType.shared_ones)

        trips = TripManager.getAllTrips()

        assertEquals(3, trips.size)
        TripManager.clearTrips()
    }

    // sorting trips via trip name returns right trip order
    @Test
    fun sortingTripsViaTripsReturnsRightTripOrder(){
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()
        TripManager.createTrip("Vienna", TripType.self_created)
        TripManager.createTrip("Berlin", TripType.self_created)
        TripManager.createTrip("Graz", TripType.self_created)

        TripManager.sortTripsBySortingInput("trip name")
        trips = TripManager.getAllTrips()

        val rigth_result = arrayListOf("Berlin", "Graz", "Vienna")
        assertEquals(trips.toString(), rigth_result.toString())

        TripManager.clearTrips()
    }
/*
    // sorting trips via start date returns right trip order
    @Test
    fun sortingTripsViaStartDateReturnsRightTripOrder(){
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()
        TripManager.createTrip("Vienna", TripType.self_created)
        var tripinfo1 = TripDate("StartDate", "2018-12-12")

        TripManager.createTrip("Berlin", TripType.self_created)
        var tripinfo2 = TripDate("StartDate", "2018-12-10")

        TripManager.createTrip("Graz", TripType.self_created)
        var tripinfo3 = TripDate("StartDate", "2018-12-14")

        trips = TripManager.getAllTrips()
        trips.get(0).addTripInformation(tripinfo1)
        trips.get(1).addTripInformation(tripinfo2)
        trips.get(2).addTripInformation(tripinfo3)

        TripManager.sortTripsBySortingInput("start date")

        val rigth_result = arrayListOf("Berlin", "Vienna", "Graz")
        assertEquals(trips.toString(), rigth_result.toString())

        TripManager.clearTrips()
    }
*/
    /*
    // sorting trips via end date returns right trip order
    @Test
    fun sortingTripsViaEndDateReturnsRightTripOrder(){
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()
        TripManager.createTrip("Vienna", TripType.self_created)
        var tripinfo1 = TripDate("EndDate", "2018-12-12")

        TripManager.createTrip("Berlin", TripType.self_created)
        var tripinfo2 = TripDate("EndDate", "2018-12-10")

        TripManager.createTrip("Graz", TripType.self_created)
        var tripinfo3 = TripDate("EndDate", "2018-12-14")

        trips = TripManager.getAllTrips()
        trips.get(0).addTripInformation(tripinfo1)
        trips.get(1).addTripInformation(tripinfo2)
        trips.get(2).addTripInformation(tripinfo3)

        TripManager.sortTripsBySortingInput("end date")

        val rigth_result = arrayListOf("Berlin", "Vienna", "Graz")
        assertEquals(trips.toString(), rigth_result.toString())

        TripManager.clearTrips()
    }
*/
    // sorting trips via total cost returns right trip order
    /*
    @Test
    fun sortingTripsViaTotalCostReturnsRightTripOrder(){

    }
     */

    /*
    // sorting trips via number of destinations returns right trip order
    @Test
    fun sortingTripsViaNumberOfDestinationsReturnsRightTripOrder(){
        TripManager.clearTrips()
        var trips: ArrayList<Trip> = ArrayList()
        TripManager.createTrip("Vienna", TripType.self_created)
        var tripinfo1 = TripDestination("TripDestinations", "3")

        TripManager.createTrip("Berlin", TripType.self_created)
        var tripinfo2 = TripDestination("TripDestinations", "5")

        TripManager.createTrip("Graz", TripType.self_created)
        var tripinfo3 = TripDestination("TripDestinations", "4")

        trips = TripManager.getAllTrips()
        trips.get(0).addTripInformation(tripinfo1)
        trips.get(1).addTripInformation(tripinfo2)
        trips.get(2).addTripInformation(tripinfo3)

        TripManager.sortTripsBySortingInput("number of destinations")

        val rigth_result = arrayListOf("Berlin", "Vienna", "Graz")
        assertEquals(trips.toString(), rigth_result.toString())

        TripManager.clearTrips()
    }
     */

}