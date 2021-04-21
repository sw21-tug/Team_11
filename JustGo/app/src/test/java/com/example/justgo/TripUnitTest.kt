package com.example.justgo

import com.example.justgo.Entitys.Trip
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
}