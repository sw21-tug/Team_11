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
                if(foods.foods.isNotEmpty()){
                    it.tripInformations.add(foods)
                    it.tripInformations.remove(foods.name)
                }
            }
            var destinations = databaseHelper.viewDestinationbyTrip(it)
            if(destinations != null){
                if(destinations.destinations.isNotEmpty()){
                    it.tripInformations.add(destinations)
                    it.tripInformations.remove(destinations.name)
                }
            }
            var dates = databaseHelper.viewDatebyTrip(it)
            if (dates != null) {
                if(dates.dates.isNotEmpty()){
                    it.tripInformations.add(dates)
                    it.tripInformations.remove(dates.name)
                }
            }
            var picture = databaseHelper.viewPictureorVideobyTrip(it)
            if(picture != null){
                if(picture.picturesAndVideos.isNotEmpty()){
                    it.tripInformations.add(picture)
                    it.tripInformations.remove(picture.name)
                }
            }
            println(it.tripInformations.toString())
        }

        TripManager.addTripsFromDatabase(trips)
    }
}