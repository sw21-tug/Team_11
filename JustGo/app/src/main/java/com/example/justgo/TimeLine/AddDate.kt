package com.example.justgo.TimeLine

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.MainActivity
import com.example.justgo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class AddDate : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_date)

        val discard :FloatingActionButton
        discard=findViewById(R.id.discard_floatActionButton)
        discard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val save :FloatingActionButton = findViewById(R.id.saveDate_floatActionButton)
        save.setOnClickListener {
            val dateString:EditText = findViewById(R.id.date_EditText)
            val description:EditText = findViewById(R.id.description_EditText)
            val descriptionText = description.text.toString()
            var date : LocalDateTime? = null
            try {
                date = LocalDateTime.parse(dateString.text.toString())
            }
            catch (e: DateTimeParseException){
            }

            var resultIntent = Intent()

            if (date != null && descriptionText != ""){
                resultIntent.putExtra("date", date)
                resultIntent.putExtra("description", descriptionText)
                setResult(Activity.RESULT_OK, resultIntent)
            }

            // TODO: Add message for invalid input
            finish()

        }
    }
}