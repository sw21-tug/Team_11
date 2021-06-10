package com.example.justgo.TimeLine

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripDates
import com.example.justgo.Logic.TripManager
import com.example.justgo.R
import java.io.Serializable
import java.time.LocalDateTime

class TimeLine : AppCompatActivity() {
    private lateinit var trip : Trip
    private lateinit var dates : TripDates
    private val REQUEST_CODE = 0
    private lateinit var listView  : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_line)

        trip = intent.getSerializableExtra("trip") as Trip
        dates = getTripDates()

        listView = findViewById<ListView>(R.id.date_list)
        listView.adapter = TimeLineAdapter(this, dates.dates)

        val add_date_button = findViewById<Button>(R.id.add_date_button)
        add_date_button.setOnClickListener{
            addDate()
        }
    }

    fun getTripDates() : TripDates{
        return (trip.getTripInformationbyName("Dates") as TripDates)
    }

    fun addDate(){
        val intent = Intent(this, AddDate::class.java).apply {}
        val datekeys = dates.dates.keys
        val arraydates = ArrayList(datekeys)
        intent.putExtra("dates", arraydates as Serializable)
        startActivityForResult(intent, REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val date = data.getSerializableExtra("date") as LocalDateTime
                val description = data.getSerializableExtra("description") as String

                var timelineDatabaseHelper = DatabaseHelper(this)
                timelineDatabaseHelper.addDate(description, date.toString(), trip)

                dates.dates[date] = description

                (listView.adapter as TimeLineAdapter).notifyDataSetChanged()

//                trip.tripInformations.remove("Dates")
//                trip.tripInformations.add(dates)
                TripManager.replaceTrip(
                    TripManager.getTripbyName(trip.nameofTrip).first(),
                    trip
                )
            }
        }
    }
}

