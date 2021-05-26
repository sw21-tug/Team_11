package com.example.justgo.Logic

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Before

import org.junit.Assert.*

import org.junit.Test

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
        var trip = Trip("TestTrip",TripType.self_created)
        DestinationsRestCallManager.getDestinationFromRESTService("Graz",context,trip)

        assertEquals("Graz",trip.destinations.first().getName())
    }
}