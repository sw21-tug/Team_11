package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Food
import com.example.justgo.Entitys.FoodType
import com.example.justgo.Entitys.Trip
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewFoodActivity : AppCompatActivity() {

    private lateinit var food_dropdown : Spinner
    private lateinit var trip : Trip
    private lateinit var discardButton : FloatingActionButton
    private lateinit var saveButton : FloatingActionButton
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_food)

        trip = intent.getSerializableExtra("trip") as Trip
        food_dropdown = findViewById(R.id.foodType_dorpdown)
        food_dropdown.adapter = ArrayAdapter<FoodType>(this, android.R.layout.simple_spinner_item, FoodType.values())
        discardButton = findViewById(R.id.discardFood_floatActionButton)
        saveButton = findViewById(R.id.saveFood_floatActionButton)
        nameEditText = findViewById(R.id.foodName_EditText)
        locationEditText = findViewById(R.id.foodLocation_EditText)

        discardButton.setOnClickListener {
            val intent = Intent(this, FoodsActivity::class.java)
            intent.putExtra("trip", trip)
            startActivity(intent)
        }

        saveButton.setOnClickListener {

            if(!(nameEditText.text.toString().equals("")) && !(locationEditText.text.toString().equals(""))) {

                trip.addFood(nameEditText.text.toString(), locationEditText.text.toString(), food_dropdown.selectedItem as FoodType)
                var foodDatabaseHelper = DatabaseHelper(this)

                TripManager.replaceTrip(
                    TripManager.getTripbyName(trip.nameofTrip).first(),
                    trip
                )
                foodDatabaseHelper.addFood(Food(nameEditText.text.toString(), locationEditText.text.toString(), food_dropdown.selectedItem as FoodType),trip)
                val intent = Intent(this, FoodsActivity::class.java)
                intent.putExtra("trip", trip)
                startActivity(intent)

            }
        }
    }
}