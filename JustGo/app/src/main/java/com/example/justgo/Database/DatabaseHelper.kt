package com.example.justgo.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.justgo.Entitys.*
import java.io.File
import java.time.LocalDateTime

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 5

        private val DATABASE_NAME = "TripDatabase"
        private val TABLE_TRIP = "TRIP"
        private val TABLE_FOOD = "FOOD"
        private val TABLE_LOCATION = "LOCATION"
        private val TABLE_DATE = "TIME"
        private val TABLE_PICTURE = "PICTURE"



        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_TripType = "tripType"
        private val KEY_FOODTYPE = "foodType"
        private val KEY_LOCATION = "location"
        private val FOREIGNKEY_TRIPID = "tripID"
        private val KEY_LAT = "lat"
        private val KEY_LONG = "longi"
        private val KEY_DATE = "date"
        private val KEY_URI = "uri"
        private val KEY_PICTUREVIDEOTYPE = "picturevideotype"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_TRIP_TABLE = ("CREATE TABLE " + TABLE_TRIP + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_TripType + " TEXT"+")")
        db?.execSQL(CREATE_TRIP_TABLE)

        val CREATE_FOOD_TABLE = ("CREATE TABLE " + TABLE_FOOD + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_FOODTYPE + " TEXT,"+
                KEY_LOCATION +" TEXT,"+ FOREIGNKEY_TRIPID +" INTEGER, FOREIGN KEY("+ FOREIGNKEY_TRIPID +") REFERENCES "+ TABLE_TRIP + "("+ KEY_ID +"))")
        db?.execSQL(CREATE_FOOD_TABLE)

        val CREATE_LOCATION_TABLE = ("CREATE TABLE " + TABLE_LOCATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_LAT + " TEXT,"+
                KEY_LONG +" TEXT,"+ FOREIGNKEY_TRIPID +" INTEGER, FOREIGN KEY("+ FOREIGNKEY_TRIPID +") REFERENCES "+ TABLE_TRIP + "("+ KEY_ID +"))")
        db?.execSQL(CREATE_LOCATION_TABLE)

        val CREATE_DATE_TABLE = ("CREATE TABLE " + TABLE_DATE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_DATE +
                " TEXT,"+ FOREIGNKEY_TRIPID +" INTEGER, FOREIGN KEY("+ FOREIGNKEY_TRIPID +") REFERENCES "+ TABLE_TRIP + "("+ KEY_ID +"))")
        db?.execSQL(CREATE_DATE_TABLE)

        val CREATE_PICTURE_TABLE = ("CREATE TABLE " + TABLE_PICTURE + "("
                + KEY_URI + " String PRIMARY KEY," + KEY_PICTUREVIDEOTYPE + " TEXT,"
                + FOREIGNKEY_TRIPID +" INTEGER, FOREIGN KEY("+ FOREIGNKEY_TRIPID +") REFERENCES "+ TABLE_TRIP + "("+ KEY_ID +"))")
        db?.execSQL(CREATE_PICTURE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURE)
        onCreate(db)
    }
    fun addPictureorVideo(picture: Picture, trip:Trip):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_URI, picture.uri_.path)
        contentValues.put(KEY_PICTUREVIDEOTYPE, picture.type_.name)
        contentValues.put(FOREIGNKEY_TRIPID,trip.getID())


        // Inserting Row
        val success = db.insert(TABLE_PICTURE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun viewPictureorVideobyTrip(trip: Trip): PictureVideoList? {

        val pictureVideoList = PictureVideoList()
        try {
            val selectQuery = "SELECT  * FROM ${TABLE_PICTURE} where " + FOREIGNKEY_TRIPID + "=" + trip.getID()
            val db = this.readableDatabase
            var cursor: Cursor? = null
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: SQLiteException) {
                db.execSQL(selectQuery)
                return null
            }
            var uri_path: String
            var type: String
            if (cursor.moveToFirst()) {
                do {
                    uri_path = cursor.getString(cursor.getColumnIndex(KEY_URI))
                    type = cursor.getString(cursor.getColumnIndex(KEY_PICTUREVIDEOTYPE))
                    //ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val picture = Picture(Uri.fromFile(File(uri_path)), PictureVideoType.valueOf(type))
                    pictureVideoList.picturesAndVideos.add(picture)
                } while (cursor.moveToNext())
            }
        }catch (e:SQLiteException){
            if (e.message?.contains("no such table") == true){
                Log.e(ContentValues.TAG, "Creating table " + TABLE_FOOD + "because it doesn't exist!" )
                println("Creating table " + TABLE_FOOD + "because it doesn't exist!" )
                // create table
                // re-run query, etc.
            }
        }
        return pictureVideoList
    }
    fun addFood(food: Food, trip:Trip):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, food._foodName)
        contentValues.put(KEY_FOODTYPE, food._foodType.name)
        contentValues.put(KEY_LOCATION,food._location)
        contentValues.put(FOREIGNKEY_TRIPID,trip.getID())


        // Inserting Row
        val success = db.insert(TABLE_FOOD, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun deleteFood(food: Food):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, food.foodID)
        val success = db.delete(TABLE_FOOD, "id=" + food.foodID, null)
        db.close()
        return success
    }

    fun viewFoodbyTrip(trip: Trip): TripFood? {

        val tripfood = TripFood("Foods")
        try {
            val selectQuery = "SELECT  * FROM ${TABLE_FOOD} where " + FOREIGNKEY_TRIPID + "=" + trip.getID()
            val db = this.readableDatabase
            var cursor: Cursor? = null
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: SQLiteException) {
                db.execSQL(selectQuery)
                return null
            }
            var name: String
            var foodType: String
            var location: String
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                    foodType = cursor.getString(cursor.getColumnIndex(KEY_FOODTYPE))
                    //ID = cursor.getInt(cursor.getColumnIndex("id"))
                    location = cursor.getString(cursor.getColumnIndex(KEY_LOCATION))
                    val food = Food(name, location, FoodType.valueOf(foodType))
                    food.foodID = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    tripfood.foods.add(food)
                } while (cursor.moveToNext())
            }
        }catch (e:SQLiteException){
            if (e.message?.contains("no such table") == true){
                Log.e(ContentValues.TAG, "Creating table " + TABLE_FOOD + "because it doesn't exist!" )
                println("Creating table " + TABLE_FOOD + "because it doesn't exist!" )
                // create table
                // re-run query, etc.
            }
        }
        return tripfood
    }

    //method to insert data
    fun addTrip(trip: Trip):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, trip.nameofTrip)
        contentValues.put(KEY_TripType, trip.tripType.name)

        // Inserting Row
        val success = db.insert(TABLE_TRIP, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun viewTrip():ArrayList<Trip>{
        val tripList:ArrayList<Trip> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_TRIP"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var ID:Int
        var name:String
        var tripType:String
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"))
                tripType = cursor.getString(cursor.getColumnIndex("tripType"))
                ID = cursor.getInt(cursor.getColumnIndex("id"))
                val trip = Trip(name, TripType.valueOf(tripType))
                trip.addID(ID)

                tripList.add(trip)
            } while (cursor.moveToNext())
        }
        return tripList

    }

    fun addDestination(destination: Destination, trip:Trip):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, destination.name_)
        contentValues.put(KEY_LONG, destination.longit_)
        contentValues.put(KEY_LAT, destination.letit_)
        contentValues.put(FOREIGNKEY_TRIPID, trip.getID())


        // Inserting Row
        val success = db.insert(TABLE_LOCATION, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun deleteDestination(destination: Destination):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, destination.destinationID)
        val success = db.delete(TABLE_LOCATION, "id=" + destination.destinationID, null)
        db.close()
        return success
    }

    fun viewDestinationbyTrip(trip: Trip):TripDestination?{

        var tripDestination =  TripDestination("Locations")
        try {
            val selectQuery = "SELECT  * FROM ${TABLE_LOCATION} where " + FOREIGNKEY_TRIPID + "=" + trip.getID()
            val db = this.readableDatabase
            var cursor: Cursor? = null
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: SQLiteException) {
                db.execSQL(selectQuery)
                return null
            }
            var name: String
            var longi: String
            var lat: String
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                    longi = cursor.getString(cursor.getColumnIndex(KEY_LONG))
                    //ID = cursor.getInt(cursor.getColumnIndex("id"))
                    lat = cursor.getString(cursor.getColumnIndex(KEY_LAT))
                    val destination = Destination(name, longi.toDouble(), lat.toDouble())
                    destination.destinationID = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    tripDestination.destinations.add(destination)
                } while (cursor.moveToNext())
            }
        }catch (e:SQLiteException){
            if (e.message?.contains("no such table") == true){
                Log.e(ContentValues.TAG, "Creating table " + TABLE_LOCATION + "because it doesn't exist!" )
                println("Creating table " + TABLE_LOCATION + "because it doesn't exist!" )
                // create table
                // re-run query, etc.
            }
        }
        return tripDestination
    }

    fun addDate(name: String, timestamp:String, trip:Trip):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_DATE, timestamp)
        contentValues.put(FOREIGNKEY_TRIPID, trip.getID())


        // Inserting Row
        val success = db.insert(TABLE_DATE, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun viewDatebyTrip(trip: Trip): TripDates? {
        val tripdates = TripDates("Dates")
        val dateList: ArrayList<TripDates> = ArrayList()
        try {
            val selectQuery = "SELECT  * FROM ${TABLE_DATE} where " + FOREIGNKEY_TRIPID + "=" + trip.getID()
            val db = this.readableDatabase
            var cursor: Cursor? = null
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: SQLiteException) {
                db.execSQL(selectQuery)
                return null
            }
            var name: String
            var value: String
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                    value = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                    //ID = cursor.getInt(cursor.getColumnIndex("id"))

                    tripdates.dates.put(LocalDateTime.parse(value), name)
                } while (cursor.moveToNext())
            }
        }catch (e:SQLiteException){
            if (e.message?.contains("no such table") == true){
                Log.e(ContentValues.TAG, "Creating table " + TABLE_LOCATION + "because it doesn't exist!" )
                println("Creating table " + TABLE_LOCATION + "because it doesn't exist!" )
                // create table
                // re-run query, etc.
            }
        }
        return tripdates
    }

    //method to update data
    /*fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL, emp.userEmail) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues, "id=" + emp.userId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/
    //method to delete data
    /*fun deleteEmployee(emp: Trip):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, "id=" + emp.userId, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/
}