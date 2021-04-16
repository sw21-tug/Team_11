package com.example.justgo.Entitys

import java.io.Serializable

abstract class TripInformation(name:String, value:Any) : Serializable{
    var name:String = name
    var value = value
}

class TripDate(name : String, value: String) : TripInformation(name, value){
}

class TripDestination(name : String, value: String) : TripInformation(name, value){

}