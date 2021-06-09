package com.example.justgo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.example.justgo.Database.DatabaseHelper
import com.example.justgo.Entitys.Picture
import com.example.justgo.Entitys.PictureVideoList
import com.example.justgo.Entitys.PictureVideoType
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileNotFoundException


class PictureVideoActivity : AppCompatActivity() {

    private lateinit var beforeButton: Button
    private lateinit var fromButton: Button
    private lateinit var backButton: Button
    private lateinit var deleteButton: Button
    private lateinit var addButton: FloatingActionButton
    private lateinit var gridViewBefore: GridView
    private lateinit var gridViewDuring : GridView
    private lateinit var chosenGridView : GridView
    private lateinit var previewImage: ImageView
    private lateinit var previewVideo : VideoView
    private var openGallery: Int = 100
    private lateinit var trip : Trip
    private lateinit var pictureVideoInformation : PictureVideoList
    private var selectedType: PictureVideoType = PictureVideoType.taken_before_trip
    private var pictureVideoListDuring: ArrayList<Uri> = ArrayList()
    private var pictureVideoListBefore: ArrayList<Uri> = ArrayList()
    private var currentShownURI : Uri? = null

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictures_and_videos)

        //get permissions for pictures
        verifyStoragePermissions(this)

        trip = intent.getSerializableExtra("trip") as Trip
        pictureVideoInformation = trip.getTripInformationbyName("Pictures and Videos")!! as PictureVideoList

        previewImage = findViewById(R.id.preview_imageView)
        previewVideo = findViewById(R.id.preview_videoView)
        beforeButton = findViewById(R.id.pictures_and_videos_before_button)
        fromButton = findViewById(R.id.pictures_and_videos_from_button)
        addButton = findViewById(R.id.pictures_and_videos_add_button)
        backButton = findViewById(R.id.pictures_and_videos_back_button)
        deleteButton = findViewById(R.id.pictures_and_videos_delete_button)
        gridViewBefore = findViewById(R.id.pictures_and_videos_gridView_before)
        gridViewDuring = findViewById(R.id.pictures_and_videos_gridView_from)

        chosenGridView = gridViewBefore

        val pictureVideoDir : File = File(this.filesDir, "pictures_videos/" + trip.nameofTrip)
        pictureVideoDir.walkBottomUp().forEach {
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
        pictureVideoListBefore = pictureVideoInformation.getPicturesVideosList(PictureVideoType.taken_before_trip)
        pictureVideoListDuring = pictureVideoInformation.getPicturesVideosList(PictureVideoType.taken_during_trip)

        gridViewBefore.adapter  = PictureVideoAdapter(this, pictureVideoListBefore)
        gridViewDuring.adapter =  PictureVideoAdapter(this, pictureVideoListDuring)

        endPreview() // Hide all views and buttons associated with previews
        switchSelectedPictureType()
        beforeButton.setOnClickListener {
            selectedType = PictureVideoType.taken_before_trip
            switchSelectedPictureType()
        }

        fromButton.setOnClickListener {
            selectedType = PictureVideoType.taken_during_trip
            switchSelectedPictureType()
        }

        addButton.setOnClickListener {
            openGalleryForImage()
        }

        backButton.setOnClickListener {
            endPreview()
        }

        deleteButton.setOnClickListener {
            val pathOfCurrentUri = currentShownURI?.path
            val currentShownUriFile : File = File("", pathOfCurrentUri)
            currentShownUriFile.delete()

            pictureVideoInformation.deletePictureOrVideo(currentShownURI, selectedType)
            if (selectedType == PictureVideoType.taken_during_trip){
                pictureVideoListDuring = pictureVideoInformation.getPicturesVideosList(PictureVideoType.taken_during_trip)
                gridViewDuring.adapter = PictureVideoAdapter(this, pictureVideoListDuring)
            }
            else{
                pictureVideoListBefore = pictureVideoInformation.getPicturesVideosList(PictureVideoType.taken_before_trip)
                gridViewBefore.adapter = PictureVideoAdapter(this, pictureVideoListBefore)
            }

            endPreview()
            (chosenGridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
            chosenGridView.invalidateViews()
        }

        gridViewDuring.setOnItemClickListener { parent, view, position, id ->
            startPreview(position)
        }
        gridViewBefore.setOnItemClickListener { parent, view, position, id ->
            startPreview(position)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun switchSelectedPictureType()
    {
        if(selectedType == PictureVideoType.taken_during_trip)
        {
            pictureVideoListDuring = pictureVideoInformation.getPicturesVideosList(selectedType)
            gridViewDuring.visibility = View.VISIBLE
            gridViewBefore.visibility = View.GONE
            chosenGridView = gridViewDuring
            fromButton.isClickable = false
            beforeButton.isClickable = true

            fromButton.setBackgroundColor(R.color.purple_500)
            beforeButton.setBackgroundColor(Color.BLUE)
        }
        else
        {
            pictureVideoListBefore  = pictureVideoInformation.getPicturesVideosList(selectedType)
            gridViewBefore.visibility = View.VISIBLE
            gridViewDuring.visibility = View.GONE
            chosenGridView = gridViewBefore
            beforeButton.isClickable = false
            fromButton.isClickable = true

            beforeButton.setBackgroundColor(R.color.purple_500)
            fromButton.setBackgroundColor(Color.BLUE)
        }
    }

    private fun endPreview()
    {
        backButton.visibility = View.GONE
        deleteButton.visibility = View.GONE
        previewVideo.visibility = View.GONE
        previewImage.visibility = View.GONE
        beforeButton.visibility = View.VISIBLE
        fromButton.visibility = View.VISIBLE
        addButton.visibility = View.VISIBLE
        chosenGridView.visibility = View.VISIBLE
    }

    private fun startPreview(position : Int)
    {
        beforeButton.visibility = View.GONE
        fromButton.visibility = View.GONE
        addButton.visibility = View.GONE
        chosenGridView.visibility = View.GONE
        backButton.visibility = View.VISIBLE
        deleteButton.visibility = View.VISIBLE
        val element: Uri? = chosenGridView.adapter.getItem(position) as Uri?
        currentShownURI = element
        if (element.toString().endsWith(".mp4")) {
            previewVideo.setVideoURI(element)
            previewVideo.visibility = View.VISIBLE

            val mediaControls = MediaController(this)
            mediaControls.setAnchorView(previewVideo)
            previewVideo.setMediaController(mediaControls)
            previewVideo.requestFocus()
            previewVideo.setZOrderOnTop(true)
            previewVideo.start()
        }
        else {
            previewImage.setImageURI(element)
            previewImage.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        // Overrides back button to close preview
        if (previewImage.visibility == View.VISIBLE || previewVideo.visibility == View.VISIBLE){
            // If a preview is open, close it
            endPreview()
        }
        else{
            // If not, use normal back button behaviour
            super.onBackPressed()
        }
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "*/*"
        startActivityForResult(intent, openGallery)
    }


    private fun getPath(uri: Uri?, activity: Activity): String {
        var cursor: Cursor? = null
        try {
            val projection = arrayOf(MediaStore.MediaColumns.DATA)
            cursor = activity.contentResolver.query(uri!!, projection, null, null, null)
            if (cursor!!.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                return cursor.getString(column_index)
            }
        } catch (e: Exception) {
        } finally {
            cursor!!.close()
        }
        return ""
    }


    private fun getDestPath(isPhoto : Boolean) : String{
        var path = "pictures_videos/" + trip.nameofTrip + "/"
        path += if (selectedType == PictureVideoType.taken_before_trip){
            "before"
        } else {
            "during"
        }

        val size = if (selectedType == PictureVideoType.taken_before_trip) {
            pictureVideoInformation.getPicturesVideosList(selectedType).size
        } else {
            pictureVideoInformation.getPicturesVideosList(selectedType).size
        }

        path += "/$size"
        path += if (isPhoto) ".jpg" else ".mp4"

        return path
    }


    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     * @param activity
     */
    private fun verifyStoragePermissions(activity: Activity?) {
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

                val selectedImageUri = data.data!!
                var imgPath = getPath(selectedImageUri, this);
                val url = data.data.toString();

                val isPicture = !imgPath.endsWith(".mp4")
                val file:File = File("", imgPath)
                val destFile = File(this.filesDir, getDestPath(isPicture))

                if (url.startsWith("content://com.google.android.apps.photos.content")){
                    // Photo is only available on google photos cloud
                    try {
                        file.copyTo(destFile, overwrite = true)
                    } catch (e : java.lang.Exception) {
                        e.printStackTrace()

                        try {
                            val inputStream = this.contentResolver.openInputStream(selectedImageUri);
                            if (inputStream != null) {
                                this.contentResolver.openOutputStream(destFile.toUri()).use { fileOut ->
                                    inputStream.copyTo(fileOut!!)
                                }
                            }
                        }
                        catch (e: FileNotFoundException ) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    file.copyTo(destFile, overwrite = true)
                }

                pictureVideoInformation.addPictureVideo(destFile.toUri(), selectedType)
                val databaseHelper = DatabaseHelper(this)
                databaseHelper.addPictureorVideo(Picture(destFile.toUri(),selectedType),trip)
                if (selectedType == PictureVideoType.taken_before_trip){
                    pictureVideoListBefore = pictureVideoInformation.getPicturesVideosList(selectedType)
                    gridViewBefore.adapter = PictureVideoAdapter(this, pictureVideoListBefore)
                }
                else{
                    pictureVideoListDuring = pictureVideoInformation.getPicturesVideosList(selectedType)
                    gridViewDuring.adapter = PictureVideoAdapter(this, pictureVideoListDuring)

                }
                (chosenGridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
                chosenGridView.invalidateViews()
            }
        }
    }
}