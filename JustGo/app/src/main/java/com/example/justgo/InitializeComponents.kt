package com.example.justgo

import android.app.Application
import com.example.justgo.Logic.TripManager

class InitializeComponents: Application() {

    override fun onCreate() {
        super.onCreate()
        TripManager.createSampleTrips()
    }
}