package com.example.justgo.Logic

import android.content.Context
import android.os.Handler
import androidx.test.core.app.ApplicationProvider
import org.junit.Before

import org.junit.Assert.*

import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Test

import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
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
        DestinationManager.clearDestinations()
        DestinationManager.changeActualOpenTrip("Graz")
        DestinationManager.getDestinationFromRESTService("Graz",context)
        delay(5000L)
        var destinations = DestinationManager.getDestinationsForActualTrip()
        var destination = destinations.first()
        assertEquals("Graz",destination.getName())
    }
}