package com.example.justgo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.justgo.Entitys.Cost
import com.example.justgo.Entitys.CostsList
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewCostActivity : AppCompatActivity() {

    private lateinit var trip : Trip
    private lateinit var costsList : CostsList
    private lateinit var descriptionUserInput : EditText
    private lateinit var amountUserInput : EditText
    private lateinit var saveButton : FloatingActionButton
    private lateinit var discardButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_cost)

        trip = intent.getSerializableExtra("trip") as Trip
        costsList = trip.getTripInformationbyName("Costs") as CostsList
        descriptionUserInput = findViewById(R.id.costDescription_EditText)
        amountUserInput = findViewById(R.id.costAmount_EditText)
        saveButton = findViewById(R.id.saveCost_floatActionButton)
        discardButton = findViewById(R.id.discardCost_floatActionButton)

        saveButton.setOnClickListener {
            if(!(descriptionUserInput.text.toString().equals("")) && !(amountUserInput.text.toString().equals("")))
            {
                var costDesc = descriptionUserInput.text.toString()
                var amount = amountUserInput.text.toString()
                var cost = Cost(costDesc, amount)

                costsList.addCost(cost)
            }
            finish()
        }
    }
}