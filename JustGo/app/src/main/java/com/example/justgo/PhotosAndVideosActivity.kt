package com.example.justgo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.justgo.Entitys.Trip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.channels.FileChannel


class PhotosAndVideosActivity : AppCompatActivity() {

    private lateinit var beforeButton: Button
    private lateinit var fromButton: Button
    private lateinit var addButton: FloatingActionButton
    private lateinit var gridView: GridView
    private var openGallary: Int = 100
    private lateinit var trip : Trip
    private var selectedType: PictureVideoType = PictureVideoType.taken_before_trip
    private var currentPictureVideoList: ArrayList<Uri> = ArrayList()
    private var context : Context = this

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

    fun getPath(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    @Throws(IOException::class)
    private fun copyFile(sourceFile: File, destFile: File) {
        if (!sourceFile.exists()) {
            return
        }
        var source: FileChannel? = null
        var destination: FileChannel? = null
        source = FileInputStream(sourceFile).getChannel()
        destination = FileOutputStream(destFile).getChannel()
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size())
        }
        if (source != null) {
            source.close()
        }
        if (destination != null) {
            destination.close()
        }
    }

    fun getDestPath() : String{
        var path = "photos_videos/" + trip.nameofTrip + "/"
        if (selectedType == PictureVideoType.taken_before_trip){
            path += "before"
        }
        else {
            path += "during"
        }
        path += "/" + currentPictureVideoList.size.toString()
        return path
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == openGallary){
            if(data?.data != null)
            {

                val imgPath = getPath(data.data!!)
    /*
                var fileInputStream: FileInputStream? = null
                fileInputStream = openFileInput(imgPath)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }

                val out: OutputStream
                val root = Environment.getExternalStorageDirectory().absolutePath + "/"
                val createDir = File(root + "Folder Name" + File.separator)
                if (!createDir.exists()) {
                    createDir.mkdir()
                }
                //val file = File(root + "Folder Name" + File.separator + "Name of File")
                file.createNewFile()
                out = FileOutputStream(file)

                out.write(attr.data)
                out.close()
//Displaying data on EditText
                fileData.setText(stringBuilder.toString()).toString()
*/



                val file = File(context.getExternalFilesDir(context.getExternalFilesDir(null)?.absolutePath), imgPath)
                val destFile = File(context.filesDir, getDestPath())
                file.copyTo(destFile, overwrite = true)
//                copyFile(file, destFile)
                println("Saved image to: " + destFile.path)

                trip.addPictureVideo(destFile.toUri(), selectedType)
                currentPictureVideoList= trip.getPicturesVideosList(selectedType)
                (gridView.adapter as PictureVideoAdapter).notifyDataSetChanged()
                gridView.invalidateViews()
            }
        }
    }
}