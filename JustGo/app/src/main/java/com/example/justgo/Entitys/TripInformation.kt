package com.example.justgo.Entitys

abstract class TripInformation(name:String) {
    var name:String
        get() {
            return name
        }
        set(value) {
            name=value
        }
    init {
        this.name = name
    }
}