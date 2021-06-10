package com.example.justgo.Food

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.justgo.Entitys.Food
import com.example.justgo.R


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyFoodRecyclerViewAdapter(
    private var values: ArrayList<Food>
) : RecyclerView.Adapter<MyFoodRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item._foodName
        holder.accomodation.text = item._location
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val accomodation: TextView = view.findViewById(R.id.accomodation)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    fun setItems(list : ArrayList<Food>){
        values = ArrayList()
        values.addAll(list)
    }
}