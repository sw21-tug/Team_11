package com.example.justgo.Entitys

class CoTraveller(name : String) {
    private var name_ = name
    private var tasks_ : ArrayList<String> = ArrayList()
    private var spendings_ : ArrayList<String> = ArrayList()

    fun addTask(task : String)
    {
        tasks_.add(task)
    }
    fun addSpending(spending : String)
    {
        tasks_.add(spending)
    }
    fun deleteTask(task : String)
    {
        tasks_.remove(task)
        /*
        tasks_.forEach {
            if(it.equals(task))
            {
                tasks_.remove(it)
            }
        }
         */
    }
    fun deleteSpending(spending : String)
    {
        spendings_.remove(spending)
        /*
        spendings_.forEach {
            if(it.equals(spending))
            {
                spendings_.remove(it)
            }
        }

         */
    }
}