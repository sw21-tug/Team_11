package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateTrip : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_trip)

        val discard :FloatingActionButton
        discard=findViewById(R.id.discard_floatActionButton)
        discard.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val save :FloatingActionButton
        save=findViewById(R.id.saveTrip_floatActionButton)
        save.setOnClickListener {
            val name:EditText
            name=findViewById(R.id.tripName_EditText)

            if(!(name.text.toString().equals(""))) {


                val databaseHelper: DatabaseHelper = DatabaseHelper(this)
                databaseHelper.addTrip(Trip(name.text.toString(),TripType.self_created))
                var trips = databaseHelper.viewTrip()
                var lasttrip = trips.last()
                TripManager.createTrip(lasttrip)
                TripManager.getAllTrips().forEach {
                    println(it.toString())
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}