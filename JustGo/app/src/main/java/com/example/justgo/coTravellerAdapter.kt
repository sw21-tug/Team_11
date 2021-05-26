package com.example.justgo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.justgo.Entitys.CoTraveller
import com.example.justgo.Entitys.CoTravellersList

class coTravellerAdapter(context: Context, private val dataSource: CoTravellersList) : BaseAdapter() {

    private val context = context
    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
        var coTraveller = getItem(position)
        val view = inflater.inflate(R.layout.list_item_cotraveller, parent, false)
        val cardView = view.rootView.findViewById<CardView>(R.id.card_view)

        var nameView = view.rootView.findViewById<TextView>(R.id.traveller_name)
        nameView.text = coTraveller.name

        return cardView
    }
}