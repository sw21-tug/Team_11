package com.example.justgo.Entitys

class TripManager {
    companion object{
        private var trips: ArrayList<Trip> = ArrayList()

        fun createTrip(name:String,tripType:TripType){
            val trip = Trip(name,tripType)
            trips.add(trip)
        }
        fun getTripbyName(name:String):Trip?{
            trips.forEach {
                if(it.nameofTrip==name){
                    return it
                }
            }
            return null
        }
        fun getTripsbyType(type: TripType):ArrayList<Trip>{
            val tripsbyType : ArrayList<Trip> = ArrayList()
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
        fun sortTripsbyName(name:String){
            trips.sortBy {
                it.nameofTrip
            }
        }
    }
}