package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Cost
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripCost
import com.example.justgo.Entitys.TripInformation
import com.example.justgo.Logic.TripManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt

class CostsActivity : AppCompatActivity() {

    private lateinit var trip : Trip
    private lateinit var addButton : FloatingActionButton
    private lateinit var costsListView : RecyclerView
    private lateinit var tripCost : TripCost
    private val REQUEST_CODE = 0
    private var costDatabaseHelper = DatabaseHelper(this)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_costs)

        trip = intent.getSerializableExtra("trip") as Trip
        tripCost = getTripCosts()
        addButton = findViewById(R.id.add_cost_button)
        costsListView = findViewById(R.id.costs_listview)


        setDataFromDb()

        val trashBinIcon = resources.getDrawable(
            R.drawable.ic_baseline_delete_24,
            null
        )

        costsListView.layoutManager = LinearLayoutManager(this)
        costsListView.adapter = CostRecyclerViewAdapter(tripCost.costs)

        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var cost = tripCost.costs.get(viewHolder.adapterPosition)
                costDatabaseHelper.deleteCost(cost)
                setDataFromDb()
                (costsListView.adapter as CostRecyclerViewAdapter).setItems(tripCost.costs)
                (costsListView.adapter as CostRecyclerViewAdapter).notifyDataSetChanged()
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
        myHelper.attachToRecyclerView(costsListView)





        addButton.setOnClickListener {
            val intent = Intent(this, AddNewCostActivity::class.java)
            intent.putExtra("trip", trip)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val cost = data.getSerializableExtra("costs") as Cost

                costDatabaseHelper.addCost(cost, trip)
                trip.tripInformations.remove(tripCost)
                trip.tripInformations.add(costDatabaseHelper.viewCostbyTrip(trip) as TripInformation)

                setDataFromDb()

                TripManager.replaceTrip(
                    TripManager.getTripbyName(trip.nameofTrip).first(),
                    trip
                )
                (costsListView.adapter as CostRecyclerViewAdapter).notifyDataSetChanged()
            }
        }
    }
    fun setDataFromDb(){
        val costList = costDatabaseHelper.viewCostbyTrip(trip)
        tripCost.costs.clear()
        if (costList != null) {
            if(costList.costs.isNotEmpty()){
                tripCost.costs.addAll(costList.costs)
            }
        }
    }

    fun getTripCosts() : TripCost {
        return (trip.getTripInformationbyName("Costs") as TripCost)
    }
}