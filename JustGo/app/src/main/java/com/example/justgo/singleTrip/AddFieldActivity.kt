package com.example.justgo.singleTrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.Entitys.TripInformation
import com.example.justgo.R
import java.io.Serializable


class AddFieldActivity : AppCompatActivity() {

    private var tripinfo:List<TripInformation>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_field)

        tripinfo = intent.getSerializableExtra("tripinfolist") as List<TripInformation>

        var listView = findViewById<ListView>(R.id.item_chooser)

        listView.adapter = AddFieldAdapter(this, tripinfo!!)

        var save = findViewById<Button>(R.id.save)
        save.setOnClickListener {
            save(listView)
        }
    }

    fun save(listView: ListView){
        for(i in 0 until listView.childCount){
            var item = listView.getChildAt(i)
            var edt = item.findViewById<EditText>(R.id.value)
            if(edt.text != null){
                tripinfo!!.get(i).value = edt.text.toString()
            }
        }
        val resultIntent = Intent()
        resultIntent.putExtra("result", tripinfo as Serializable)
        setResult(Activity.RESULT_OK, resultIntent);
        finish()
    }
}