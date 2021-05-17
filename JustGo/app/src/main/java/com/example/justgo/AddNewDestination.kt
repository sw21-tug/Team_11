package com.example.justgo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Destination
import com.example.justgo.Entitys.Food
import com.example.justgo.Entitys.FoodType
import com.example.justgo.Entitys.Trip
import com.example.justgo.Logic.DestinationsRestCallManager
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewDestination : AppCompatActivity() {
    private lateinit var trip:Trip
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_destination)

        val discard : FloatingActionButton
        trip = intent.getSerializableExtra("trip") as Trip
        discard=findViewById(R.id.discard2_floatActionButton)
        discard.setOnClickListener {
            trip.getDestinationsForActualTrip().forEach {
                println(it.toString())
            }
            val intent = Intent(this,DestinationsActivity::class.java)
            intent.putExtra("trip",trip)
            startActivity(intent)
        }
        val save : FloatingActionButton
        save=findViewById(R.id.saveDestination_floatActionButton)
        save.setOnClickListener {
            val name: EditText
            name=findViewById(R.id.destination_EditText)

            if(!(name.text.toString().equals(""))) {

                savedata(name.text.toString(),this)

            }
        }
    }
    fun savedata(name:String,context: Context) {
        DestinationsRestCallManager.getDestinationFromRESTService(name,context,trip)
        trip.getDestinationsForActualTrip().forEach {
            println(it.toString())

        }
        TripManager.replaceTrip(
                TripManager.getTripbyName(trip.nameofTrip).first(),
                trip
        )
    }
}