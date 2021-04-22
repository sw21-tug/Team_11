package com.example.justgo.singleTrip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.justgo.R


class AddFieldActivity : AppCompatActivity() {

    private lateinit var possibleFields:List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_field)

        possibleFields = intent.getSerializableExtra("possibleFields") as List<String>

        val listView = findViewById<ListView>(R.id.item_chooser)
        listView.adapter = AddFieldAdapter(this, possibleFields)


        listView.setOnItemClickListener { parent, view, position, id ->
            val element = listView.adapter.getItem(position).toString() // The item that was clicked

            val resultIntent = Intent()
            resultIntent.putExtra("added_field", element)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

    }
}