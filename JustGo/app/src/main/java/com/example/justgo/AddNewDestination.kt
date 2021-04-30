package com.example.justgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.justgo.Entitys.TripType
import com.example.justgo.Logic.DestinationManager
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewDestination : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_destination)

        val discard : FloatingActionButton
        discard=findViewById(R.id.discard2_floatActionButton)
        discard.setOnClickListener {
            val intent = Intent(this,DestinationsActivity::class.java)
            startActivity(intent)
        }
        val save : FloatingActionButton
        save=findViewById(R.id.saveDestination_floatActionButton)
        save.setOnClickListener {
            val name: EditText
            name=findViewById(R.id.destination_EditText)

            if(!(name.text.toString().equals(""))) {


                println("Wird aufgerufen\n")
                DestinationManager.getDestinationFromRESTService(name.text.toString())
                DestinationManager.getDestinationsForActualTrip()?.forEach {
                    println(it.toString())
                }

                val intent = Intent(this, DestinationsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}