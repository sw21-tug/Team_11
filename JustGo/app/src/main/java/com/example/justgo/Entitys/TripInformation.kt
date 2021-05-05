package com.example.justgo.Entitys

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime

abstract class TripInformation(name:String, value:Any) : Serializable{
    var name:String = name
    var value = value
}

class TripDates(name : String, value: String) : TripInformation(name, value){
    @RequiresApi(Build.VERSION_CODES.O)
    public var dates : MutableMap<LocalDateTime, String> = mutableMapOf(LocalDateTime.parse("2021-12-24T14:55") to "Christmas")
}

class TemplateTripinfo(name: String) : TripInformation(name , "")


class TripDestination(name : String, value: String) : TripInformation(name, value)

