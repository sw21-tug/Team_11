package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Destination.DestinationRecyclerViewAdapter
import com.example.justgo.Entitys.*
import com.example.justgo.Food.MyFoodRecyclerViewAdapter
import com.example.justgo.Logic.TripManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.roundToInt

class DestinationsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var destinationListView: RecyclerView
    private lateinit var addDestinationButton: Button
    private lateinit var trip:Trip
    private lateinit var tripDestination: TripDestination
    private val REQUEST_CODE = 0
    private var destinationDatabaseHelper = DatabaseHelper(this)


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        trip = intent.getSerializableExtra("trip") as Trip
        tripDestination = getTripDestinations()
        setDataFromDb()

        val trashBinIcon = resources.getDrawable(
                R.drawable.ic_baseline_delete_24,
                null
        )

        destinationListView = findViewById(R.id.destinations_list_view)
        destinationListView.layoutManager = LinearLayoutManager(this)
        destinationListView.adapter = DestinationRecyclerViewAdapter(tripDestination.destinations)

        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var destination = tripDestination.destinations.get(viewHolder.adapterPosition)
                destinationDatabaseHelper.deleteDestination(destination)
                setDataFromDb()
                (destinationListView.adapter as DestinationRecyclerViewAdapter).setItems(tripDestination.destinations)
                (destinationListView.adapter as DestinationRecyclerViewAdapter).notifyDataSetChanged()
                drawMarker()
            }

            override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                )
                c.clipRect(0f, viewHolder.itemView.top.toFloat(),
                        dX, viewHolder.itemView.bottom.toFloat())
                c.drawColor(Color.RED)
                val textMargin = resources.getDimension(R.dimen.text_margin)
                        .roundToInt()
                trashBinIcon.bounds = Rect(
                        textMargin,
                        viewHolder.itemView.top + textMargin,
                        textMargin + trashBinIcon.intrinsicWidth,
                        viewHolder.itemView.top + trashBinIcon.intrinsicHeight
                                + textMargin
                )

                trashBinIcon.draw(c)
            }
        }

        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(destinationListView)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        addDestinationButton = findViewById(R.id.add_destination_button)
        addDestinationButton.setOnClickListener {
            val intent = Intent(this, AddNewDestination::class.java)
            intent.putExtra("trip",trip)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        drawMarker()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                TripManager.replaceTrip(
                        TripManager.getTripbyName(trip.nameofTrip).first(),
                        trip
                )

                setDataFromDb()

                (destinationListView.adapter as DestinationRecyclerViewAdapter).setItems(tripDestination.destinations)
                (destinationListView.adapter as DestinationRecyclerViewAdapter).notifyDataSetChanged()
                drawMarker()
            }
        }
    }

    fun getTripDestinations() : TripDestination {
        return (trip.getTripInformationbyName("Locations") as TripDestination)
    }

    fun setDataFromDb(){
        var destination = destinationDatabaseHelper.viewDestinationbyTrip(trip)
        tripDestination.destinations.clear()
        if (destination != null) {
            if(destination.destinations.isNotEmpty()){
                tripDestination = destination
            }
        }
    }

    fun drawMarker(){
        mMap.clear()
        var lastDestination:Destination? = null
        tripDestination.destinations.forEach {
            val dest= LatLng(it.letit_,it.longit_)
            lastDestination=it
            if(it.letit_ != 0.0 && it.longit_ != 0.0){
                mMap.addMarker(MarkerOptions().position(dest).title(it.name_))
            }
        }
        if(lastDestination!=null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lastDestination!!.letit_,
                    lastDestination!!.longit_)))
        }
    }
}