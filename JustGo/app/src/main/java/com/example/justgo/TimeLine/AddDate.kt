package com.example.justgo.TimeLine

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.MainActivity
import com.example.justgo.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import java.util.*

class AddDate : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_date)

        val datelist = intent.getSerializableExtra("dates") as ArrayList<LocalDateTime>



        val discard :FloatingActionButton
        discard=findViewById(R.id.discard_floatActionButton)
        discard.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val save :FloatingActionButton = findViewById(R.id.saveDate_floatActionButton)
        save.setOnClickListener {
            val dateString:EditText = findViewById(R.id.date_EditText)
            val timeString:EditText = findViewById(R.id.time_EditText)
            val description:EditText = findViewById(R.id.description_EditText)
            val descriptionText = description.text.toString()
            var date : LocalDateTime? = null
            try {
                date = LocalDateTime.parse(dateString.text.toString()+ "T" + timeString.text.toString())
            }
            catch (e: DateTimeParseException){
            }

            var resultIntent = Intent()
            if(date in datelist){
                Toast.makeText(this,
                        "This Time has an entry already! Please choose an unique time!",
                        Toast.LENGTH_LONG).show()
            }
            else if(date != null && descriptionText != ""){
                resultIntent.putExtra("date", date)
                resultIntent.putExtra("description", descriptionText)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            else {
                Toast.makeText(this,
                        "Please enter a valid date",
                        Toast.LENGTH_LONG).show()
            }
        }
        var textView = findViewById<EditText>(R.id.date_EditText)
        textView.setText(SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()))

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy-MM-dd" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textView.setText(sdf.format(cal.time))

        }

        textView.setOnClickListener {
            DatePickerDialog(this@AddDate, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        var timeEditText = findViewById<EditText>(R.id.time_EditText)
        timeEditText.setText(SimpleDateFormat("HH:mm").format(System.currentTimeMillis()))

        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)

            timeEditText.setText(SimpleDateFormat("HH:mm").format(cal.time))

        }

        timeEditText.setOnClickListener {
            TimePickerDialog(this@AddDate, timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true).show()
        }


    }
}