package com.example.justgo.Logic

import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType

class TripManager {
    companion object{
        private var trips: ArrayList<Trip> = ArrayList()

        fun createSampleTrips(){
            createTrip("Barcelona",TripType.Sample)
            createTrip("New York",TripType.Sample)
            createTrip("Paris",TripType.Sample)
        }
        fun createTrip(name:String,tripType: TripType){
            var input: String = name
            var trip: Trip = Trip(input.capitalize(),tripType)
            trips.add(trip)
        }
        fun createTrip(trip:Trip){
            trips.add(trip)
        }
        fun getTripbyName(name:String): ArrayList<Trip>{
            var tripsbyName:ArrayList<Trip>
            tripsbyName = ArrayList()
            trips.forEach {
                if(it.nameofTrip==name){
                    tripsbyName.add(it)
                }
            }
            return tripsbyName
        }

        fun replaceTrip(oldTrip:Trip, newTrip:Trip){
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
            trips.sortWith(compareBy({ it.nameofTrip}))
        }

        fun clearTrips() {
            trips.clear()
        }
        fun addTripsFromDatabase(tripsdb:ArrayList<Trip>){
            tripsdb.forEach {
                trips.add(it)
            }
        }
    }
}