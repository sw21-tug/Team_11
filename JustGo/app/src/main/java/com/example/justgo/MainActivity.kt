package com.example.justgo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType
import com.example.justgo.Logic.ListViewerTrips
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var list_view_of_trips : ListViewerTrips
    private val SINGLE_TRIP_ACTIVITY = 0
    private val LOCALE_KEY = "localekey"
    private val RUSSIAN_LOCALE = "ru"
    private val ENGLISH_LOCALE = "en_US"
    private val LOCALE_PREF_KEY = "localePref"

    private lateinit var locale: Locale

    var trips:ArrayList<Trip> = ArrayList()
    private lateinit var myTripsButton : Button
    private lateinit var sampleTripsButton : Button

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.INTERNET),2)
            else{
                print("Works")
            }
        }
        else{
            println("Version not okey")
        }
        val layoutid : Int = android.R.layout.simple_list_item_1
        list_view_of_trips = ListViewerTrips(
            this, layoutid, findViewById(R.id.list_view_of_trips), TripManager.getTripsbyType(
                TripType.Sample
            )
        )
        myTripsButton = findViewById(R.id.my_trips_button)
        sampleTripsButton = findViewById(R.id.sample_trips_button)
        list_view_of_trips.startListView()

        val sp = getSharedPreferences(LOCALE_PREF_KEY, Context.MODE_PRIVATE)
        val localeString = sp.getString(LOCALE_KEY, ENGLISH_LOCALE)

        val createTrip : FloatingActionButton = findViewById(R.id.createtripFloatingActionButton)
        createTrip.setOnClickListener {
            val intent = Intent(this, CreateTrip::class.java)
            startActivity(intent)
        }

        val searchTrip: FloatingActionButton = findViewById(R.id.SearchForTripBt)
        searchTrip.setOnClickListener {

            val tripname:TextView = findViewById(R.id.SearchbyNameEditText)
            if(!tripname.text.toString().equals("")){
                trips = TripManager.getTripbyName(tripname.text.toString())
                trips.forEach {
                    println(it.toString())
                }
                list_view_of_trips.changeTripsList(trips)
                list_view_of_trips.startListView()
            }
        }

        val language: Button = findViewById(R.id.language_btn)
        language.setOnClickListener {
            changeLanguage()
        }

        var currentTripType: TripType = TripType.Sample
        val list_view_trips_description: TextView
        list_view_trips_description = findViewById(R.id.list_view_description)


        myTripsButton.setOnClickListener {
            list_view_trips_description.text = this.getString(R.string.btn_mytrips)
            currentTripType = TripType.SelfCreated
            trips = TripManager.getTripsbyType(TripType.SelfCreated)

            list_view_of_trips.changeTripsList(trips)
            list_view_of_trips.startListView()

            myTripsButton.isClickable = false
            sampleTripsButton.isClickable = true
        }

        sampleTripsButton.setOnClickListener {
            list_view_trips_description.text = this.getString(R.string.btn_sampletrips)
            currentTripType = TripType.Sample
            trips = TripManager.getTripsbyType(TripType.Sample)
            list_view_of_trips.changeTripsList(trips)
            list_view_of_trips.startListView()

            sampleTripsButton.isClickable = false
            myTripsButton.isClickable = true
        }

        val dropdown_menu: Spinner = findViewById(R.id.sorting_dropdown)
        val array_ = resources.getStringArray(R.array.dropdown_string_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, array_)
        dropdown_menu.adapter = adapter
        dropdown_menu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(parent?.getItemAtPosition(position).toString().equals("trip name")){
                    TripManager.sortTripsbyName()
                    list_view_of_trips.changeTripsList(TripManager.getTripsbyType(currentTripType))
                    list_view_of_trips.startListView()
                }
            }
        }
    }

    private fun changeLanguage() {
        val resources: Resources = resources
        val sharedPreferences =
            getSharedPreferences("localePref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if (sharedPreferences.getString(LOCALE_KEY, ENGLISH_LOCALE) == RUSSIAN_LOCALE) {
            locale = Locale(ENGLISH_LOCALE)
            editor.putString(LOCALE_KEY, ENGLISH_LOCALE)
        } else {
            locale = Locale(RUSSIAN_LOCALE)
            editor.putString(LOCALE_KEY, RUSSIAN_LOCALE)
        }
        editor.apply()
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
        recreate()
    }

    override fun onResume() {
        super.onResume()
        // This ensures the trips are updated after returning from the singleTripActivity
        list_view_of_trips.changeTripsList(TripManager.getTripsbyType(TripType.Sample))
        list_view_of_trips.startListView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupPermissions() {
        val permissionInternet = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.INTERNET
        )
        if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
            println("Permission to record denied")
        }
        else{
            var string :ArrayList<String> = ArrayList<String>()
            string.add(android.Manifest.permission.INTERNET)
            var array = arrayOf<String>()
            requestPermissions(string.toArray(array), 1)
        }

        val permissionAccessNetworkState = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_NETWORK_STATE
        )
        if (permissionAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
            println("Permission to record denied")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}