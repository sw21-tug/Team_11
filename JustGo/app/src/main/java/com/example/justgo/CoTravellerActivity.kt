package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CoTravellerActivity : AppCompatActivity() {

    private lateinit var trip : Trip
    private lateinit var addButton : FloatingActionButton
    private lateinit var coTravellersListView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_co_traveller)

        trip = intent.getSerializableExtra("trip") as Trip
        addButton = findViewById(R.id.coTravellers_addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddNewCoTravellerActivity::class.java)
            intent.putExtra("trip", trip)
            this.startActivity(intent)
        }

        coTravellersListView = findViewById(R.id.coTraveller_listView)
    }


}