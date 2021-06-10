package com.example.justgo.Entitys

import junit.framework.Assert.assertEquals
import org.junit.Test

class CoTravellerTest{

    @Test
    fun addTaskUpdatesList(){
        val cotraveller = CoTraveller("testTraveller")

        cotraveller.addTask("testTask")

        assertEquals(cotraveller.getTasks(), arrayListOf("testTask"))
    }

    @Test
    fun addSpendingUpdatesList(){
        val cotraveller = CoTraveller("testTraveller")

        cotraveller.addSpending("testSpending")

        assertEquals(cotraveller.getSpendings(), arrayListOf("testSpending"))
    }
}