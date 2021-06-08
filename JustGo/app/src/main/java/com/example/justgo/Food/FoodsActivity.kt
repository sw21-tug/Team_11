package com.example.justgo.Food

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.*
import com.example.justgo.Logic.TripManager
import com.example.justgo.R
import com.example.justgo.singleTrip.ActivitySingleTrip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class FoodsActivity : AppCompatActivity() {

    private lateinit var breakfastButton : Button
    private lateinit var lunchDinnerButton : Button
    private lateinit var backbutton : FloatingActionButton
    private lateinit var addFoodButton : FloatingActionButton
    private lateinit var foodListView : RecyclerView
    private lateinit var trip : Trip
    private lateinit var tripFood: TripFood
    private val REQUEST_CODE = 0
    private lateinit var arrayAdapter: ArrayAdapter<Food>
    private lateinit var foods: ArrayList<Food>
    private var boolfoodlist: Boolean = false
    private var foodDatabaseHelper = DatabaseHelper(this)


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods)

        val trashBinIcon = resources.getDrawable(
                R.drawable.ic_baseline_delete_24,
                null
        )
        trip = intent.getSerializableExtra("trip") as Trip
        tripFood = getTripFoods()
        setDataFromDb()

        foods = tripFood.getFood(FoodType.breakfast)

        //arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, breakfast_foods)
        //foodListView.adapter = arrayAdapter

        foodListView = findViewById(R.id.food_listview)
        foodListView.layoutManager = LinearLayoutManager(this)
        foodListView.adapter = MyFoodRecyclerViewAdapter(foods)


        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var food = foods.get(viewHolder.adapterPosition)
                tripFood.deleteFood(food.foodID)
                foodDatabaseHelper.deleteFood(food)
                tripFood.foods.clear()
                setDataFromDb()
                if(boolfoodlist){
                    foods = tripFood.getFood(FoodType.lunch_dinner)
                }
                else{
                    foods = tripFood.getFood(FoodType.breakfast)
                }
                (foodListView.adapter as MyFoodRecyclerViewAdapter).setItems(foods)
                (foodListView.adapter as MyFoodRecyclerViewAdapter).notifyDataSetChanged()
            }

            override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                )
                c.clipRect(0f, viewHolder.itemView.top.toFloat(),
                        dX, viewHolder.itemView.bottom.toFloat())
                c.drawColor(Color.RED)
                val textMargin = resources.getDimension(R.dimen.text_margin)
                        .roundToInt()
                trashBinIcon.bounds = Rect(
                        textMargin,
                        viewHolder.itemView.top + textMargin,
                        textMargin + trashBinIcon.intrinsicWidth,
                        viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                                + textMargin
                )

                trashBinIcon.draw(c)
            }
        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(foodListView)
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
        boolfoodlist = false
        foods = tripFood.getFood(FoodType.breakfast)
        (foodListView.adapter as MyFoodRecyclerViewAdapter).setItems(foods)
        (foodListView.adapter as MyFoodRecyclerViewAdapter).notifyDataSetChanged()
        breakfastButton.isClickable = false
        lunchDinnerButton.isClickable = true
    }

    fun lunchClick(){
        boolfoodlist = true
        foods = tripFood.getFood(FoodType.lunch_dinner)
        (foodListView.adapter as MyFoodRecyclerViewAdapter).setItems(foods)
        (foodListView.adapter as MyFoodRecyclerViewAdapter).notifyDataSetChanged()
        lunchDinnerButton.isClickable = false
        breakfastButton.isClickable = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val food = data.getSerializableExtra("foods") as Food

                foodDatabaseHelper.addFood(food, trip)
                trip.tripInformations.remove(tripFood)
                trip.tripInformations.add(foodDatabaseHelper.viewFoodbyTrip(trip) as TripInformation)

                setDataFromDb()

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

    fun setDataFromDb(){
        var foodlist = foodDatabaseHelper.viewFoodbyTrip(trip)
        tripFood.foods.clear()
        if (foodlist != null) {
            if(foodlist.foods.isNotEmpty()){
                tripFood.foods.addAll(foodlist.foods)
            }
        }
    }
}