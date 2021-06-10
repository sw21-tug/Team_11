package com.example.justgo.Logic

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Destination
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDestination
import com.example.justgo.Entitys.TripType
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DestinationManagerTest {
    lateinit var context: Context
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun getDestinationfromRest() = runBlocking{
        // Context of the app under test.
        delay(5000L)
        var trip = Trip("TestTrip",TripType.SelfCreated)
        DestinationsRestCallManager.getDestinationFromRESTService("Graz", "Hotel",context,trip)

        //assertEquals("Graz",trip.destinations.first().getName())
    }

    @Test
    fun addDestinationToDB(){
        var dbhelper = DatabaseHelper(context)
        var trip:Trip = Trip("Barcelona",TripType.Sample)
        var destination = Destination("Barcelona", 20.0, 20.0, "Hotel")
        dbhelper.addTrip(trip)
        trip = dbhelper.viewTrip().last()
        dbhelper.addDestination(destination, trip)
        var tripdest = dbhelper.viewDestinationbyTrip(trip)
        var destDB = tripdest?.destinations?.last()
        if (destDB != null) {
            dbhelper.deleteDestination(destDB)
        }
        dbhelper.deleteTrip(trip)
        if (tripdest != null) {
            assertEquals(destination.toString(), destDB.toString())
        };

    }

    @Test
    fun deleteDestinationToDB(){
        var dbhelper = DatabaseHelper(context)
        var trip:Trip = Trip("Barcelona",TripType.Sample)
        var destination = Destination("Barcelona", 20.0, 20.0, "Hotel")
        dbhelper.addTrip(trip)
        trip = dbhelper.viewTrip().last()

        dbhelper.addDestination(destination, trip)
        destination = dbhelper.viewDestinationbyTrip(trip)?.destinations?.last()!!
        dbhelper.deleteDestination(destination)
        var tripdest = dbhelper.viewDestinationbyTrip(trip)
        dbhelper.deleteTrip(trip)
        if (tripdest != null) {
            assertEquals(0, tripdest.destinations.size)
        };

    }

}