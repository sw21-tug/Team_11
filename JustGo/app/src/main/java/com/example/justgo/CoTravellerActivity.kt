package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Entitys.CoTraveller
import com.example.justgo.Entitys.CoTravellersList
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CoTravellerActivity : AppCompatActivity() {

    private lateinit var trip : Trip
    private lateinit var addButton : FloatingActionButton
    private lateinit var coTravellersListView : ListView
    private lateinit var coTravellersList: CoTravellersList
    private val ADD_TRAVELLER_REQUEST_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_co_traveller)

        trip = intent.getSerializableExtra("trip") as Trip
        coTravellersList = trip.getTripInformationbyName("Co-Travellers") as CoTravellersList
        addButton = findViewById(R.id.coTravellers_addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddNewCoTravellerActivity::class.java)
            startActivityForResult(intent, ADD_TRAVELLER_REQUEST_CODE)
        }
        coTravellersListView = findViewById(R.id.coTraveller_listView)
        coTravellersListView.adapter = coTravellerAdapter(this, coTravellersList)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TRAVELLER_REQUEST_CODE && data != null && resultCode == Activity.RESULT_OK){

            val name = data.getSerializableExtra("name") as String
            if (name != ""){
                var traveller = CoTraveller(name)

                coTravellersList.addCoTraveller(traveller)
                (coTravellersListView.adapter as coTravellerAdapter).notifyDataSetChanged()
            }
        }
    }

}