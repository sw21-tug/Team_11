package com.example.justgo.Entitys

import java.io.Serializable

class Cost(costName: String, costValue: String) : Serializable{
    var _costName = costName
    var _costValue = costValue
}