package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.*
import com.example.justgo.Logic.TripManager
import com.example.justgo.singleTrip.ActivitySingleTrip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodsActivity : AppCompatActivity() {

    private lateinit var breakfastButton : Button
    private lateinit var lunchDinnerButton : Button
    private lateinit var backbutton : FloatingActionButton
    private lateinit var addFoodButton : FloatingActionButton
    private lateinit var foodListView : ListView
    private lateinit var trip : Trip
    private lateinit var tripFood: TripFood
    private val REQUEST_CODE = 0
    private lateinit var arrayAdapter: ArrayAdapter<Food>
    private lateinit var breakfast_foods: ArrayList<Food>
    private lateinit var lunch_dinner_foods: ArrayList<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods)

        trip = intent.getSerializableExtra("trip") as Trip
        foodListView = findViewById(R.id.food_listview)
        tripFood = getTripFoods()

        breakfast_foods = tripFood.getFood(FoodType.breakfast)
        breakfast_foods.forEach {
            System.out.println(it.toString())
        }

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breakfast_foods)
        foodListView.adapter = arrayAdapter
        breakfastButton = findViewById(R.id.breakfast_button)
        breakfastButton.isClickable = false
        lunchDinnerButton = findViewById(R.id.lunch_dinner_button)

        breakfastButton.setOnClickListener {
            breakfastClick()
        }

        lunchDinnerButton.setOnClickListener {
            lunchClick()
        }

        addFoodButton = findViewById(R.id.add_food_button)
        addFoodButton.setOnClickListener {
            val intent = Intent(this, AddNewFoodActivity::class.java)
            intent.putExtra("trip", trip)
            startActivityForResult(intent, REQUEST_CODE)
        }

        backbutton = findViewById(R.id.food_back_button)
        backbutton.setOnClickListener {
            val intent = Intent(this, ActivitySingleTrip::class.java)
            intent.putExtra("trip", trip)
            startActivity(intent)
        }

    }

    fun breakfastClick(){
        breakfast_foods = tripFood.getFood(FoodType.breakfast)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breakfast_foods)
        foodListView.adapter = arrayAdapter
        breakfastButton.isClickable = false
        lunchDinnerButton.isClickable = true
    }

    fun lunchClick(){
        lunch_dinner_foods = tripFood.getFood(FoodType.lunch_dinner)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lunch_dinner_foods)
        foodListView.adapter = arrayAdapter
        lunchDinnerButton.isClickable = false
        breakfastButton.isClickable = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val food = data.getSerializableExtra("foods") as Food

                var foodDatabaseHelper = DatabaseHelper(this)
                foodDatabaseHelper.addFood(food, trip)
                trip.tripInformations.remove(tripFood)
                trip.tripInformations.add(foodDatabaseHelper.viewFoodbyTrip(trip) as TripInformation)

                tripFood = getTripFoods()

                TripManager.replaceTrip(
                        TripManager.getTripbyName(trip.nameofTrip).first(),
                        trip
                )
                breakfastClick()
            }
        }
    }

    fun getTripFoods() : TripFood {
        return (trip.getTripInformationbyName("Foods") as TripFood)
    }
}