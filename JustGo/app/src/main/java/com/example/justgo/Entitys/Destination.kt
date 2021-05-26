package com.example.justgo.Entitys

import java.io.Serializable

class Destination(name: String, longit: Double, letit: Double):Serializable {
    var destinationID: Int = 0
    var name_: String = name
    var longit_: Double = longit
    var letit_: Double = letit

    override fun toString(): String {
        return "$name_"
    }

    fun addID(id:Int){
        destinationID = id
    }
}