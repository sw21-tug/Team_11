package com.example.justgo.Entitys

import java.io.Serializable

class Cost(costName: String, costValue: String) : Serializable{
    var costID: Int = 0
    var _costName = costName
    var _costValue = costValue
}