package com.example.justgo


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justgo.Entitys.Cost
import com.example.justgo.Entitys.Destination

class CostRecyclerViewAdapter(
    private var values: MutableList<Cost>
) : RecyclerView.Adapter<CostRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_cost, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item._costName
        holder.accomodation.text  = item._costValue
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val accomodation: TextView = view.findViewById(R.id.value)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    fun setItems(list : MutableList<Cost>){
        values = ArrayList()
        values.addAll(list)
    }
}