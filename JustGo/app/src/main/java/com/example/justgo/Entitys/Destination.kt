package com.example.justgo.Entitys

import java.io.Serializable

class Destination(name: String, longit: Double, letit: Double, accomodation: String):Serializable {
    var destinationID: Int = 0
    var name_: String = name
    var longit_: Double = longit
    var letit_: Double = letit
    var accomodation: String = accomodation

    override fun toString(): String {
        return "$name_"
    }

    fun addID(id:Int){
        destinationID = id
    }
}