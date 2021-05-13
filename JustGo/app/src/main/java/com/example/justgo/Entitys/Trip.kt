package com.example.justgo.Entitys

import android.net.Uri
import com.example.justgo.PictureVideoType
import java.io.Serializable

class Trip(name:String,tripType: TripType) : Serializable {

    var tripInformations: ArrayList<TripInformation> = ArrayList()
    var possibleFields = mutableListOf<String>("Dates", "Locations", "Photos and Videos", "Transportation", "Accommodation", "Activities", "Foods")
    var nameofTrip:String = name
    var tripType:TripType = tripType
    var foods: ArrayList<Food> = ArrayList()
    var picturesAndVideosBefore: ArrayList<Uri> = ArrayList()
    var picturesAndVideosFrom: ArrayList<Uri> = ArrayList()

    fun addPictureVideo(pictureOrVideo: Uri, type: PictureVideoType){
        if(type == PictureVideoType.taken_before_trip)
        {
            picturesAndVideosBefore.add(pictureOrVideo)
        }
        else
        {
            picturesAndVideosFrom.add(pictureOrVideo)
        }
    }

    fun getPicturesVideosList(beforeOrFromType: PictureVideoType): ArrayList<Uri>{
        var returnList: ArrayList<Uri> = ArrayList()
        if(beforeOrFromType == PictureVideoType.taken_before_trip)
        {
            returnList = picturesAndVideosBefore
        }
        else
        {
            returnList = picturesAndVideosFrom
        }
        return returnList
    }

    fun addFood(foodName: String, location: String, foodType: FoodType){
        foods.add(Food(foodName, location, foodType))
    }

    fun getFood(foodType: FoodType): ArrayList<Food>{
        var returnList: ArrayList<Food> = ArrayList()
        println("testtesttest")
        foods.forEach {
            println(it.toString())
            if(it._foodType == foodType){
                returnList.add(it)
            }
        }
        return returnList
    }

    fun addTripInformation(tripInformation:TripInformation){
        this.tripInformations.add(tripInformation)
    }

    fun getTripInformationbyName(name:String):TripInformation?{
        tripInformations.forEach {
            if(it.name == name){
                return it
            }
        }
        return null
    }

    fun getTripInformationLNameist(): ArrayList<String>{
        var tripInfoNames: ArrayList<String> = ArrayList()
        tripInformations.forEach{
            tripInfoNames.add(it.name)
        }
        return tripInfoNames
    }

    override fun toString(): String {
        return "$nameofTrip"
    }


}