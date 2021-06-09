package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddNewCoTravellerActivity : AppCompatActivity() {

//    private lateinit var trip : Trip
    private lateinit var userInput : EditText
    private lateinit var saveButton : FloatingActionButton
    private lateinit var backButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_co_traveller)

        var resultIntent = Intent()
//        trip = intent.getSerializableExtra("trip") as Trip
        userInput = findViewById(R.id.CoTravellerName_EditText)

        saveButton = findViewById(R.id.saveCoTraveller_floatActionButton)
        saveButton.setOnClickListener {
            var userInputString = userInput.text.toString()
            if(userInputString != "")
            {
                resultIntent.putExtra("name", userInputString)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            else
            {
                setResult(Activity.RESULT_CANCELED, resultIntent)
                finish()
            }
        }

        backButton = findViewById(R.id.backCoTraveller_floatActionButton)
        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }
    }
}