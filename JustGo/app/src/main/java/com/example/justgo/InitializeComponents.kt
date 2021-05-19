package com.example.justgo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.TemplateTripinfo
import com.example.justgo.Logic.TripManager

class InitializeComponents: Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        TripManager.createSampleTrips()
        val databaseHelper: DatabaseHelper = DatabaseHelper(this)
        var trips = databaseHelper.viewTrip()
        trips.forEach {
            var foods = databaseHelper.viewFoodbyTrip(it)
            if(foods !=null){
                if(foods.foods.isNotEmpty())
                {
                    it.tripInformations.add(foods)
                }
            }
            var destinations = databaseHelper.viewDestinationbyTrip(it)
            if(destinations.size > 0){
                it.destinations = destinations
                it.possibleFields.remove("Locations")
                it.addTripInformation(TemplateTripinfo("Locations"))
            }
            var dates = databaseHelper.viewDatebyTrip(it)
            if (dates != null) {
                if(dates.dates.isNotEmpty()){
                    it.tripInformations.add(dates)
                    //it.possibleFields.remove("Locations")
                    //it.addTripInformation(TemplateTripinfo("Locations"))
                }
            }
        }

        TripManager.addTripsFromDatabase(trips)
    }
}