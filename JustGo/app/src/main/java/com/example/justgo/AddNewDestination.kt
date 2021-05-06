package com.example.justgo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.justgo.Entitys.Destination
import com.example.justgo.Entitys.TripType
import com.example.justgo.Logic.DestinationManager
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AddNewDestination : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_destination)

        val discard : FloatingActionButton
        discard=findViewById(R.id.discard2_floatActionButton)
        discard.setOnClickListener {
            DestinationManager.getDestinationsForActualTrip()?.forEach {
                println(it.toString())
            }
            val intent = Intent(this,DestinationsActivity::class.java)
            startActivity(intent)
        }
        val save : FloatingActionButton
        save=findViewById(R.id.saveDestination_floatActionButton)
        save.setOnClickListener {
            val name: EditText
            println(DestinationManager.actualOpenTrip)
            name=findViewById(R.id.destination_EditText)

            if(!(name.text.toString().equals(""))) {

                savedata(name.text.toString(),this)

            }
        }
    }
    fun savedata(name:String,context: Context) {
        DestinationManager.getDestinationFromRESTService(name,context)
        DestinationManager.getDestinationsForActualTrip()?.forEach {
            println(it.toString())

        }
    }
}