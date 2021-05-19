package com.example.justgo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.example.justgo.Entitys.PictureVideoList
import com.example.justgo.Entitys.PictureVideoType
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


class PictureVideoActivity : AppCompatActivity() {

    private lateinit var beforeButton: Button
    private lateinit var fromButton: Button
    private lateinit var addButton: FloatingActionButton
    private lateinit var gridView: GridView
    private var openGallery: Int = 100
    private lateinit var trip : Trip
    private lateinit var pictureVideoInformation : PictureVideoList
    private var selectedType: PictureVideoType = PictureVideoType.taken_before_trip
    private var currentPictureVideoList: ArrayList<Uri> = ArrayList()
    private var context : Context = this
    private lateinit var preview_image: ImageView
    private lateinit var preview_video: VideoView

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictures_and_videos)


        // Possible todo: Check if there are photos and videos already in the photo_video/<tripname> folder and add to the gridview
        // Add a way of deleting photos/videos

        trip = intent.getSerializableExtra("trip") as Trip
        pictureVideoInformation = trip.getTripInformationbyName("Pictures and Videos")!! as PictureVideoList
        gridView = findViewById(R.id.pictures_and_videos_gridView)
        currentPictureVideoList = pictureVideoInformation.getPicturesVideosList(selectedType)
        val pictureAdapterBefore: PictureVideoAdapter = PictureVideoAdapter(this, pictureVideoInformation.getPicturesVideosList(PictureVideoType.taken_before_trip))
        val pictureAdapterDuring: PictureVideoAdapter = PictureVideoAdapter(this, pictureVideoInformation.getPicturesVideosList(PictureVideoType.taken_during_trip))
        gridView.adapter = pictureAdapterDuring

        var pictureVideoDir : File = File(context.filesDir, "pictures_videos/" + trip.nameofTrip)
        pictureVideoDir.walkBottomUp().forEach {
            println(it.path)
            if(!it.path.endsWith(".mp4") && !it.path.endsWith(".jpg")){
                return@forEach
            }

            var type : PictureVideoType = PictureVideoType.taken_before_trip
            if ("during" in it.path){
                type = PictureVideoType.taken_during_trip
            }
            if (!pictureVideoInformation.getPicturesVideosList(type).contains( it.toUri() )){
                pictureVideoInformation.addPictureVideo(it.toUri(), type)
            }
        }

        beforeButton = findViewById(R.id.pictres_and_videos_before_button)
        beforeButton.setOnClickListener {
            selectedType = PictureVideoType.taken_before_trip
            currentPictureVideoList = pictureVideoInformation.getPicturesVideosList(selectedType)
            gridView.adapter = pictureAdapterBefore
            //(gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
            //gridView.invalidateViews()
            beforeButton.isClickable = false
            fromButton.isClickable = true
        }

        fromButton = findViewById(R.id.pictures_and_videos_from_button)
        fromButton.setOnClickListener {
            selectedType = PictureVideoType.taken_during_trip
            currentPictureVideoList = pictureVideoInformation.getPicturesVideosList(selectedType)
            gridView.adapter = pictureAdapterDuring
            //(gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
            //gridView.invalidateViews()
            fromButton.isClickable = false
            beforeButton.isClickable = true
        }

        addButton = findViewById(R.id.pictures_and_videos_add_button)
        addButton.setOnClickListener {
            openGalleryForImage()
        }

        preview_video = findViewById(R.id.preview_videoView)
        preview_image = findViewById(R.id.preview_imageView)
        gridView.setOnItemClickListener { parent, view, position, id ->
            val element: Uri? = gridView.adapter.getItem(position) as Uri?
            if (element.toString().endsWith(".mp4")) {
                preview_video.setVideoURI(element)
                preview_video.visibility = View.VISIBLE

                val mediaControls = MediaController(this)
                mediaControls.setAnchorView(preview_video)
                preview_video.setMediaController(mediaControls)
                preview_video.requestFocus()
                preview_video.setZOrderOnTop(true)
                preview_video.start()
                preview_video.setOnClickListener {
                    preview_video.visibility = View.GONE
                }
            }
            else {
                preview_image.setImageURI(element)
                preview_image.visibility = View.VISIBLE
                preview_image.setOnClickListener {
                    preview_image.visibility = View.GONE
                }
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "*/*"
        startActivityForResult(intent, openGallery)
    }

    fun getPath(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    fun getDestPath(isPhoto : Boolean) : String{
        var path = "pictures_videos/" + trip.nameofTrip + "/"
        if (selectedType == PictureVideoType.taken_before_trip){
            path += "before"
        }
        else {
            path += "during"
        }
        path += "/" + currentPictureVideoList.size.toString()

        if (isPhoto){
            path += ".jpg"
        }else{
            path += ".mp4"
        }

        return path
    }


    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == openGallery){
            if(data?.data != null)
            {
                verifyStoragePermissions(this)

                val imgPath = getPath(data.data!!)
                val isPicture = !imgPath.endsWith(".mp4")
                var file:File = File("", imgPath)
                val destFile = File(context.filesDir, getDestPath(isPicture))
                file.copyTo(destFile, overwrite = true)

                pictureVideoInformation.addPictureVideo(destFile.toUri(), selectedType)
//                TripManager.replaceTrip(
//                        TripManager.getTripbyName(trip.nameofTrip).first(),
//                        trip)
                currentPictureVideoList= pictureVideoInformation.getPicturesVideosList(selectedType)
                (gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
                gridView.invalidateViews()
            }
        }
    }
}