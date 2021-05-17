package com.example.justgo.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.justgo.Entitys.Food
import com.example.justgo.Entitys.FoodType
import com.example.justgo.Entitys.Trip
import com.example.justgo.Entitys.TripType

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 2

        private val DATABASE_NAME = "TripDatabase"
        private val TABLE_Trips = "TripTable"
        private val TABLE_FOOD = "TABLE_FOOD"


        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_TripType = "tripType"
        private val KEY_FOODTYPE = "foodType"
        private val KEY_LOCATION = "location"
        private val FOREIGNKEY_TRIPID = "tripID"
        private val TABLE_TRIP = "TripTable"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_Trips + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_TripType + " TEXT"+")")
        db?.execSQL(CREATE_CONTACTS_TABLE)

        val CREATE_FOOD_TABLE = ("CREATE TABLE " + TABLE_FOOD + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_FOODTYPE + " TEXT,"+
                KEY_LOCATION +" TEXT,"+ FOREIGNKEY_TRIPID +" INTEGER, FOREIGN KEY("+ FOREIGNKEY_TRIPID +") REFERENCES "+ TABLE_TRIP + "("+ KEY_ID +"))")
        db?.execSQL(CREATE_FOOD_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_Trips)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD)
        onCreate(db)
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
    fun viewFoodbyTrip(trip: Trip):ArrayList<Food>{

        val foodList: ArrayList<Food> = ArrayList()
        try {
            val selectQuery = "SELECT  * FROM ${TABLE_FOOD} where " + FOREIGNKEY_TRIPID + "=" + trip.getID()
            val db = this.readableDatabase
            var cursor: Cursor? = null
            try {
                cursor = db.rawQuery(selectQuery, null)
            } catch (e: SQLiteException) {
                db.execSQL(selectQuery)
                return ArrayList()
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
                    foodList.add(food)
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
        return foodList

    }
    //method to insert data
    fun addTrip(trip: Trip):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, trip.nameofTrip)
        contentValues.put(KEY_TripType, trip.tripType.name)

        // Inserting Row
        val success = db.insert(TABLE_Trips, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun viewTrip():ArrayList<Trip>{
        val tripList:ArrayList<Trip> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_Trips"
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