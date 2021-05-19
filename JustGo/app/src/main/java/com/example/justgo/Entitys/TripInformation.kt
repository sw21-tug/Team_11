package com.example.justgo.Entitys

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime

abstract class TripInformation(name:String, value:Any) : Serializable{
    var name:String = name
    var value = value
}

class TripDates(name : String) : TripInformation(name, value=""){
    @RequiresApi(Build.VERSION_CODES.O)
    public var dates : MutableMap<LocalDateTime, String> = mutableMapOf()
}

class TemplateTripinfo(name: String) : TripInformation(name , "")


class TripDestination(name : String) : TripInformation(name, value=""){
    public var destinations : MutableList<Destination> = mutableListOf()
}

class TripFood(name : String) : TripInformation(name, value=""){
    public var foods : MutableList<Food> = mutableListOf()

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
}