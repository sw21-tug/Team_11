package com.example.justgo

import android.app.Application
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.TemplateTripinfo
import com.example.justgo.Logic.TripManager

class InitializeComponents: Application() {

    override fun onCreate() {
        super.onCreate()
        TripManager.createSampleTrips()
        val databaseHelper: DatabaseHelper = DatabaseHelper(this)
        var trips = databaseHelper.viewTrip()
        trips.forEach {
            var foods = databaseHelper.viewFoodbyTrip(it)
            if(foods.size>0){
                println(it.toString())
                System.out.println(foods.toString())
                it.foods = foods
                it.possibleFields.remove("Foods")
                it.addTripInformation(TemplateTripinfo("Foods"))
            }
        }

        TripManager.addTripsFromDatabase(trips)
    }
}