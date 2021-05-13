package com.example.justgo

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PictureVideoAdapter(context : Context, private val dataSource: ArrayList<Uri>) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.list_item_picture_video, parent, false)
        val picture_video_view: ImageView = view.rootView.findViewById<ImageView>(R.id.picture_video_imageView)
        picture_video_view.setImageURI(getItem(position))
        picture_video_view.setScaleType(ImageView.ScaleType.CENTER_CROP)
        return picture_video_view
    }

    override fun getItem(position: Int): Uri? {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }
}