package com.example.justgo.Entitys

import java.io.Serializable

class Food(foodName: String, location: String, foodType: FoodType) : Serializable {
    var foodID: Int = 0
    var _foodType: FoodType = foodType
    var _foodName: String = foodName
    var _location: String = location

    override fun toString(): String {
        return "$_foodName\n\n$_location"
    }

    fun addID(id:Int){
        foodID = id
    }
}