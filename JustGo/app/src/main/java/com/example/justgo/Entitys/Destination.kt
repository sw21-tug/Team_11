package com.example.justgo.Entitys

class Destination(name: String, longit: Double, letit: Double) {

    private var name_: String = name
    private var longit_: Double = longit
    private var letit_: Double = letit

    fun getName() : String{
        return name_
    }

    fun getLongit() : Double{
        return longit_
    }

    fun getLetit() : Double{
        return letit_
    }

    override fun toString(): String {
        return "Destination(name_='$name_', longit_=$longit_, letit_=$letit_)"
    }

}