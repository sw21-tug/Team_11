package com.example.justgo

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Entitys.Food
import com.example.justgo.Entitys.FoodType
import com.example.justgo.Entitys.Trip
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FoodsActivity : AppCompatActivity() {

    private lateinit var breakfastButton : Button
    private lateinit var lunchDinnerButton : Button
    private lateinit var backButton : FloatingActionButton
    private lateinit var addFoodButton : FloatingActionButton
    private lateinit var foodListView : ListView
    private lateinit var trip : Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods)

        trip = intent.getSerializableExtra("trip") as Trip
        foodListView = findViewById(R.id.food_listview)
        breakfastButton = findViewById(R.id.breakfast_button)
        lunchDinnerButton = findViewById(R.id.lunch_dinner_button)
        addFoodButton = findViewById(R.id.add_food_button)

        val breakfast_foods = trip.getFood(FoodType.breakfast)
        breakfast_foods.forEach {
            System.out.println(it.toString())
        }
        val arrayAdapter: ArrayAdapter<Food>
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breakfast_foods)
        foodListView.adapter = arrayAdapter

        breakfastButton.setOnClickListener {
            val breakfast_foods = trip.getFood(FoodType.breakfast)
            breakfast_foods.forEach {
                System.out.println(it.toString())
            }
            val arrayAdapter: ArrayAdapter<Food>
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breakfast_foods)
            foodListView.adapter = arrayAdapter
            breakfastButton.isClickable = false
            lunchDinnerButton.isClickable = true
        }
        breakfastButton.isClickable = false


        lunchDinnerButton.setOnClickListener {
            val lunch_dinner_foods = trip.getFood(FoodType.lunch_dinner)
            lunch_dinner_foods.forEach {
                System.out.println(it.toString())
            }
            val arrayAdapter: ArrayAdapter<Food>
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lunch_dinner_foods)
            foodListView.adapter = arrayAdapter
            lunchDinnerButton.isClickable = false
            breakfastButton.isClickable = true
        }

        addFoodButton.setOnClickListener {
            val intent = Intent(this, AddNewFoodActivity::class.java)
            intent.putExtra("trip", trip)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        trip = TripManager.getTripbyName(trip.nameofTrip).first()
        if (! breakfastButton.isClickable){
            breakfastButton.callOnClick()
        }
        else{
            lunchDinnerButton.callOnClick()
        }
    }
}