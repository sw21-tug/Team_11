package com.example.justgo.Entitys

class CoTraveller(public var name : String) {
    private var tasks_ : ArrayList<String> = ArrayList()
    private var spendings_ : ArrayList<String> = ArrayList()

    fun addTask(task : String)
    {
        tasks_.add(task)
    }
    fun addSpending(spending : String)
    {
        spendings_.add(spending)
    }
    fun deleteTask(task : String)
    {
        tasks_.remove(task)
    }

    fun getTasks() : ArrayList<String>{
        val tasks = ArrayList<String>()
        tasks.addAll(tasks_)
        return  tasks
    }

    fun getSpendings() : ArrayList<String>{
        val spendings = ArrayList<String>()
        spendings.addAll(spendings_)
        return  spendings
    }

    fun deleteSpending(spending : String)
    {
        spendings_.remove(spending)
    }
}