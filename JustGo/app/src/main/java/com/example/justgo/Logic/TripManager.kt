package com.example.justgo.Logic

import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType

class TripManager {
    companion object{
        private var trips: ArrayList<Trip> = ArrayList()
        fun createSampleTrips(){
            createTrip("Barcelona",TripType.created_by_others)
            createTrip("New York",TripType.self_created)
            createTrip("Paris",TripType.shared_ones)
        }
        fun createTrip(name:String,tripType: TripType){
            var trip: Trip = Trip(name,tripType)
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
            return tripsbyName;
        }
        fun getTripsbyType(type: TripType):ArrayList<Trip>{
            var tripsbyType : ArrayList<Trip> = ArrayList();
            trips.forEach {
                if(it.tripType==type){
                    tripsbyType.add(it)
                }
            }
            return tripsbyType
        }

        fun getAllTrips():ArrayList<Trip>{
            return trips;

        }
 /*       fun sortTripsbyName(name: String){
            trips.sortBy {
                name
            }
        }*/
        fun clearTrips() {
            trips.clear()
        }
    }
}