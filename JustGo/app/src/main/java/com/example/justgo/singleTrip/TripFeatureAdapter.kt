package com.example.justgo.singleTrip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.justgo.Entitys.TripInformation
import com.example.justgo.R


class TripFeatureAdapter(private val context : Context,
                         private val dataSource: List<TripInformation>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = inflater.inflate(R.layout.list_item_trip_feature, parent, false)
        var name = view.rootView.findViewById<TextView>(R.id.name)
        name.text = dataSource[position].name

        var value = view.rootView.findViewById<TextView>(R.id.value)
        value.text = dataSource[position].value.toString()

        return view
    }
}