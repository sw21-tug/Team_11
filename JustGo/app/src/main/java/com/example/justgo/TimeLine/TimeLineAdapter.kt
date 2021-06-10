package com.example.justgo.TimeLine

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.justgo.R
import com.github.vipulasri.timelineview.TimelineView
import java.time.LocalDateTime

class TimeLineAdapter(context: Context,
            private val dataSource: Map<LocalDateTime, String>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        var keys = dataSource.keys.toList().sorted()
        val date = keys[position]
        return Pair(date, dataSource[date])
    }

    override fun getItemId(position: Int): Long {
        return  position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.list_item_date, parent, false)
        val name = view.rootView.findViewById<TextView>(R.id.date)
        val descriptionView = view.rootView.findViewById<TextView>(R.id.text_timeline_title)
        val dateTime = (getItem(position) as Pair<*, *>).first as LocalDateTime
        val description = (getItem(position) as Pair<*, *>).second

        name.text = "${dateTime.toLocalDate()} - ${dateTime.toLocalTime()}"
        descriptionView.text = description.toString()

        val timeLine = view.rootView.findViewById<TimelineView>(R.id.timeline)
        when(position){
            0 -> timeLine.initLine(1)
            count - 1 -> timeLine.initLine(2)
            else -> timeLine.initLine(0)
        }

        return view
    }
}

