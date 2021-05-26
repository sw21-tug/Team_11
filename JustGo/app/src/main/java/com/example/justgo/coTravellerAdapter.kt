package com.example.justgo

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.justgo.Entitys.CoTraveller
import com.example.justgo.Entitys.CoTravellersList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class coTravellerAdapter(context: Context, private val dataSource: CoTravellersList) : BaseAdapter() {

    private val context = context
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val maxTextLength : Int = 80

    override fun getCount(): Int {
        return dataSource.coTravellersList.size
    }

    override fun getItem(position: Int): CoTraveller {
        return dataSource.coTravellersList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val coTraveller = getItem(position)
        val view = inflater.inflate(R.layout.list_item_cotraveller, parent, false)
        val cardView = view.rootView.findViewById<CardView>(R.id.card_view)

        val nameView = view.rootView.findViewById<TextView>(R.id.traveller_name)
        nameView.text = coTraveller.name

        val deleteTravellerButton = view.rootView.findViewById<FloatingActionButton>(R.id.delete_co_traveller_button)
        deleteTravellerButton.setOnClickListener(){
            dataSource.deleteCoTraveller(coTraveller)
            this.notifyDataSetChanged()
        }

        val taskList = view.rootView.findViewById<TextView>(R.id.task_list)
        val taskHeading = view.rootView.findViewById<TextView>(R.id.tasks_heading)

        taskList.setOnClickListener(){
            addTask(position, coTraveller)
        }
        taskHeading.setOnClickListener {
            addTask(position, coTraveller)
        }

        var taskText = ""
        for (task in coTraveller.getTasks()) {
            taskText += "$task, "
        }
        if (taskText.length > 2) {
            // Remove trailing comma and whitespace
            taskText = taskText.substring(0, taskText.length - 2)
        }
        if (taskText.length > maxTextLength){
            taskText = taskText.substring(0, maxTextLength) + "..."
        }
        taskList.text = taskText

        val costList = view.rootView.findViewById<TextView>(R.id.cost_list)
        val costHeading = view.rootView.findViewById<TextView>(R.id.costs_heading)
        costList.setOnClickListener {
            addCost(position, coTraveller)
        }
        costHeading.setOnClickListener {
            addCost(position, coTraveller)
        }

        var costText = ""
        for (task in coTraveller.getSpendings()) {
            costText += "$task, "
        }
        if (costText.length > 2){
            costText = costText.substring(0, costText.length-2) // Remove trailing comma and whitespace
        }
        if (costText.length > maxTextLength){
            costText = costText.substring(0, maxTextLength) + "..."
        }
        costList.text = costText


        return cardView
    }

    private fun addTask(position: Int, coTraveller: CoTraveller) {
        var newTask = ""

        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("New task")
        val input = EditText(context)
        input.setHint("Enter new task for ${coTraveller.name}")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            newTask = input.text.toString()
            dataSource.coTravellersList[position].addTask(newTask)
            notifyDataSetChanged()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }

    private fun addCost(position: Int, coTraveller: CoTraveller) {
        var newCost = ""

        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("New Cost")
        val input = EditText(context)
        input.setHint("Enter new Cost for ${coTraveller.name}")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            newCost = input.text.toString()
            dataSource.coTravellersList[position].addSpending(newCost)
            notifyDataSetChanged()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }
}