package com.example.justgo.Destination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justgo.Entitys.Destination
import com.example.justgo.R

class DestinationRecyclerViewAdapter(
        private var values: MutableList<Destination>
) : RecyclerView.Adapter<DestinationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_destination, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.name_
        holder.accomodation.text  = item.accomodation
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

    fun setItems(list : MutableList<Destination>){
        values = ArrayList()
        values.addAll(list)
    }
}