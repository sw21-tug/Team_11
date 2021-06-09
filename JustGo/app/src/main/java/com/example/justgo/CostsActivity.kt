package com.example.justgo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.justgo.Entitys.CoTraveller
import com.example.justgo.Entitys.Cost
import com.example.justgo.Entitys.CostsList
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CostsActivity : AppCompatActivity() {

    private lateinit var trip : Trip
    private lateinit var addButton : FloatingActionButton
    private lateinit var costsListView : RecyclerView
    private lateinit var costsList : CostsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_costs)

        trip = intent.getSerializableExtra("trip") as Trip
        costsList = trip.getTripInformationbyName("Costs") as CostsList
        addButton = findViewById(R.id.add_cost_button)
        costsListView = findViewById(R.id.costs_listview)

        addButton.setOnClickListener {
            val intent = Intent(this, AddNewCostActivity::class.java)
            intent.putExtra("trip", trip)
            this.startActivity(intent)
        }
    }
}