package com.example.justgo.Entitys

import java.io.Serializable

class Destination(name: String, longit: Double, letit: Double):Serializable {

    var name_: String = name
    var longit_: Double = longit
    var letit_: Double = letit

    override fun toString(): String {
        return "Destination(name_='$name_', longit_=$longit_, letit_=$letit_)"
    }

}