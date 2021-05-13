package com.example.justgo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PhotosAndVideosActivity : AppCompatActivity() {

    private lateinit var beforeButton: Button
    private lateinit var fromButton: Button
    private lateinit var addButton: FloatingActionButton
    private lateinit var gridView: GridView
    private var openGallary: Int = 100
    private lateinit var trip : Trip
    private var selectedType: PictureVideoType = PictureVideoType.taken_before_trip
    private var currentPictureVideoList: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos_and_videos)

        trip = intent.getSerializableExtra("trip") as Trip
        gridView = findViewById(R.id.pictures_and_videos_gridView)
        currentPictureVideoList = trip.getPicturesVideosList(selectedType)
        val pictureAdapter: PictureVideoAdapter = PictureVideoAdapter(this, currentPictureVideoList)
        gridView.adapter = pictureAdapter

        beforeButton = findViewById(R.id.pictres_and_videos_before_button)
        beforeButton.setOnClickListener {
            selectedType = PictureVideoType.taken_before_trip
            currentPictureVideoList = trip.getPicturesVideosList(selectedType)
            //(gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
            gridView.invalidateViews()
            println(currentPictureVideoList)
        }

        fromButton = findViewById(R.id.pictures_and_videos_from_button)
        fromButton.setOnClickListener {
            selectedType = PictureVideoType.taken_during_trip
            currentPictureVideoList = trip.getPicturesVideosList(selectedType)
            //(gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
            gridView.invalidateViews()
            println(currentPictureVideoList)
        }

        addButton = findViewById(R.id.pictures_and_videos_add_button)
        addButton.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, openGallary)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == openGallary){
            if(data?.data != null)
            {
                trip.addPictureVideo(data.data!!, selectedType)
                currentPictureVideoList= trip.getPicturesVideosList(selectedType)
                //(gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
                gridView.invalidateViews()
            }
        }
    }
}