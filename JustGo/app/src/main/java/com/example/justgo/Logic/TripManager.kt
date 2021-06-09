package com.example.justgo.Logic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.justgo.Entitys.*
import com.example.justgo.R
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime


class TripManager {
    companion object{
        private var trips: ArrayList<Trip> = ArrayList()

        @RequiresApi(Build.VERSION_CODES.O)
        fun createSampleTrips(context : Context){
            val barcelona : Trip = Trip("Barcelona", TripType.Sample)

            var dates = TripDates()
            var date = LocalDateTime.parse("2022-05-01T08:00")
            dates.dates[date] = "Plane from Wien"

            date = LocalDateTime.parse("2022-05-01T14:00")
            dates.dates[date] = "Check in at hotel"

            date = LocalDateTime.parse("2022-05-02T10:00")
            dates.dates[date] = "Visit Sagrada Familia"

            date = LocalDateTime.parse("2022-05-02T17:00")
            dates.dates[date] = "Dinnner at La Gastronomica"

            date = LocalDateTime.parse("2022-05-03T18:00")
            dates.dates[date] = "Barcelona vs AC Milan"

            date = LocalDateTime.parse("2022-05-05T13:30")
            dates.dates[date] = "Plane back home"

            barcelona.addTripInformation(dates)

            var foods = TripFood()
            foods.foods.add(Food("Tapas", "La Gastronomica", FoodType.lunch_dinner))
            foods.foods.add(Food("Creme Catalonia", "La Gastronomica", FoodType.lunch_dinner))

            foods.foods.add(Food("Breakfast Buffet", "Hotel", FoodType.breakfast))
            barcelona.addTripInformation(foods)

            var locations = TripDestination()
            locations.destinations.add(Destination("Camp Nou", 2.122820, 41.380898, ""))
            locations.destinations.add(Destination("Sagrada Familia", 2.173504, 41.403706, ""))

            barcelona.addTripInformation(locations)


            var destPathBase = "pictures_videos/Barcelona/during/"
            var pictureList = listOf(R.drawable.barcelona, R.drawable.barcelona2, R.drawable.barcelona3)

            saveBitMaps(destPathBase, pictureList, context)
            barcelona.addTripInformation(PictureVideoList())

            var coTravellersList = CoTravellersList()
            var cotraveller = CoTraveller("Mike")
            cotraveller.addSpending("Hotel: 170€")
            cotraveller.addSpending("Tickets for Sagrada Familia: 30€")
            cotraveller.addSpending("Tickets for football game: 70€")
            cotraveller.addTask("Find restaurant for third day")
            coTravellersList.addCoTraveller(cotraveller)

            cotraveller = CoTraveller("Adam")
            cotraveller.addSpending("Plane tickets: 170€")
            cotraveller.addTask("Buy tickets for public transport")
            cotraveller.addTask("Find restaurant for third day")
            coTravellersList.addCoTraveller(cotraveller)

            barcelona.addTripInformation(coTravellersList)

            trips.add(barcelona)


            val newYork = Trip("New York", TripType.Sample)

            dates = TripDates()

            date = LocalDateTime.parse("2021-12-20T14:00")
            dates.dates[date] = "Plane from Graz"

            date = LocalDateTime.parse("2021-12-21T10:00")
            dates.dates[date] = "Visit Empire State Building"

            date = LocalDateTime.parse("2022-12-21T17:00")
            dates.dates[date] = "Dinner at Times Square"

            date = LocalDateTime.parse("2022-12-22T12:00")
            dates.dates[date] = "Visit Central Park"

            date = LocalDateTime.parse("2022-12-23T13:30")
            dates.dates[date] = "Brooklyn Bridge"

            date = LocalDateTime.parse("2022-12-25T08:00")
            dates.dates[date] = "Christmas!"

            newYork.addTripInformation(dates)


            foods = TripFood()
            foods.foods.add(Food("Burgers", "Times Square", FoodType.lunch_dinner))
            foods.foods.add(Food("Bagels", "Street vendor", FoodType.breakfast))
            foods.foods.add(Food("Chinese food", "Chinatown", FoodType.lunch_dinner))
            foods.foods.add(Food("Coffee", "SoHo", FoodType.breakfast))

            newYork.addTripInformation(foods)

            locations = TripDestination()
            locations.destinations.add(Destination("Times Square", -73.985130, 40.758896, ""))
            locations.destinations.add(Destination("Central Park", -73.968285, 	40.785091, ""))
            locations.destinations.add(Destination("Brooklyn Bridge", -73.997002,40.706001, ""))
            locations.destinations.add(Destination("Statue of Liberty", -74.044502, 40.689247, ""))

            newYork.addTripInformation(locations)

            coTravellersList = CoTravellersList()
            cotraveller = CoTraveller("Maria")
            cotraveller.addSpending("Hotel: 470€")
            cotraveller.addTask("Get Empire State tickets")
            coTravellersList.addCoTraveller(cotraveller)

            cotraveller = CoTraveller("Hanne")
            cotraveller.addSpending("Plane tickets: 220€")
            cotraveller.addTask("Buy christmas presents")
            cotraveller.addTask("Find christmas decorations for hotel room")
            coTravellersList.addCoTraveller(cotraveller)

            newYork.addTripInformation(coTravellersList)


            destPathBase = "pictures_videos/New York/during/"
            pictureList = listOf(R.drawable.newyork1, R.drawable.newyork2, R.drawable.newyork0)
            saveBitMaps(destPathBase, pictureList, context)
            newYork.addTripInformation(PictureVideoList())

            trips.add(newYork)


            val paris = Trip("Paris", TripType.Sample)
            dates = TripDates()

            date = LocalDateTime.parse("2021-07-15T12:00")
            dates.dates[date] = "Pick up rental car at Graz Airport"

            date = LocalDateTime.parse("2021-07-15T20:00")
            dates.dates[date] = "Arrive at airBnB in Stuttgart"

            date = LocalDateTime.parse("2021-07-16T16:00")
            dates.dates[date] = "Arrive in Paris"

            date = LocalDateTime.parse("2021-07-16T19:00")
            dates.dates[date] = "Dinner at Arpège"

            date = LocalDateTime.parse("2021-07-17T12:00")
            dates.dates[date] = "Visit Eiffel Tower"

            date = LocalDateTime.parse("2021-07-17T08:00")
            dates.dates[date] = "Day trip to Le Havre"

            date = LocalDateTime.parse("2021-07-18T14:00")
            dates.dates[date] = "Visit Versailles"

            date = LocalDateTime.parse("2021-07-20T23:00")
            dates.dates[date] = "Deliver rental car"

            paris.addTripInformation(dates)

            locations = TripDestination()
            locations.destinations.add(Destination("Stuttgart", 9.183333, 48.783333, "AirBnB"))
            locations.destinations.add(Destination("Eiffel Tower", 2.294694,48.858093, ""))
            locations.destinations.add(Destination("Graz airport", 15.4368,46.9892, ""))
            locations.destinations.add(Destination("Versailles", 2.130122, 48.801407, ""))
            locations.destinations.add(Destination("Le Havre", 0.100000, 49.490002, ""))

            paris.addTripInformation(locations)

            foods = TripFood()
            foods.foods.add(Food("Croissant", "", FoodType.breakfast))
            foods.foods.add(Food("Boeuf Bourguignon", "", FoodType.lunch_dinner))
            foods.foods.add(Food("Omellete du fromage", "", FoodType.breakfast))
            foods.foods.add(Food("Soupe á l'oignon", "", FoodType.lunch_dinner))
            foods.foods.add(Food("quiche lorraine", "", FoodType.breakfast))

            paris.addTripInformation(foods)

            destPathBase = "pictures_videos/Paris/before/"
            pictureList = listOf(R.drawable.paris0, R.drawable.paris1, R.drawable.paris2, R.drawable.paris3)
            saveBitMaps(destPathBase, pictureList, context)
            paris.addTripInformation(PictureVideoList())

            coTravellersList = CoTravellersList()
            cotraveller = CoTraveller("John")
            cotraveller.addSpending("Hotel: 170€")
            cotraveller.addSpending("AirBnB: 40€")
            cotraveller.addSpending("Arpege dinner: 700€")
            cotraveller.addTask("Plan route for driving")

            coTravellersList.addCoTraveller(cotraveller)

            cotraveller = CoTraveller("Mark")
            cotraveller.addSpending("Rental car: 300€")
            cotraveller.addTask("Check covid status border crossings")
            coTravellersList.addCoTraveller(cotraveller)

            cotraveller = CoTraveller("Matthew")
            cotraveller.addTask("Find restaurant in Le Havre")
            cotraveller.addTask("Create roadtrip playlist")
            coTravellersList.addCoTraveller(cotraveller)

            paris.addTripInformation(coTravellersList)

            trips.add(paris)

        }

        private fun saveBitMaps(destPathBase: String, pictureList : List<Int>, context: Context){
            for (i in 0..pictureList.size-1){
                val file: File = File(context.filesDir, destPathBase + i.toString() + ".jpg")

                if (file.exists()){
                    continue
                }
                val bm = BitmapFactory.decodeResource(context.resources, pictureList[i])
                file.parentFile.mkdirs()
                val outStream = FileOutputStream(file)
                file.createNewFile()
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
                outStream.close()
            }

        }

        fun createTrip(name: String, tripType: TripType) : Trip{
            val input: String = name
            val trip: Trip = Trip(input.capitalize(), tripType)

            trips.add(trip)
            return trip
        }
        fun createTrip(trip: Trip){
            trips.add(trip)
        }
        fun getTripbyName(name: String): ArrayList<Trip>{
            var tripsbyName:ArrayList<Trip>
            tripsbyName = ArrayList()
            trips.forEach {
                if(it.nameofTrip==name){
                    tripsbyName.add(it)
                }
            }
            return tripsbyName
        }

        fun replaceTrip(oldTrip: Trip, newTrip: Trip){
            var index = trips.indexOf(oldTrip)
            trips.set(index, newTrip)
        }

        fun getTripsbyType(type: TripType):ArrayList<Trip>{
            var tripsbyType : ArrayList<Trip> = ArrayList()
            trips.forEach {
                if(it.tripType==type){
                    tripsbyType.add(it)
                }
            }
            return tripsbyType
        }

        fun getAllTrips():ArrayList<Trip>{
            return trips

        }

        fun sortTripsbyName() {
            trips.sortWith(compareBy({ it.nameofTrip }))
        }

        fun clearTrips() {
            trips.clear()
        }
        fun addTripsFromDatabase(tripsdb: ArrayList<Trip>){
            tripsdb.forEach {
                trips.add(it)
            }
        }
    }
}