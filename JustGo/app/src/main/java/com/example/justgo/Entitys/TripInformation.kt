package com.example.justgo.Entitys

import android.graphics.Picture
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime

abstract class TripInformation(name:String, value:Any) : Serializable{
    var name:String = name
    var value = value
}

class TripDates(name : String) : TripInformation(name, value=""){
    @RequiresApi(Build.VERSION_CODES.O)
    public var dates : MutableMap<LocalDateTime, String> = mutableMapOf(LocalDateTime.parse("2021-12-24T14:55") to "Christmas")
}

class CoTravellersList() : TripInformation("CoTravellers", ""){
    var coTravellersList : ArrayList<CoTraveller> = ArrayList()

    fun addCoTraveller(coTraveller: CoTraveller){
        coTravellersList.add(coTraveller)
    }
    fun deleteCoTraveller(coTraveller: CoTraveller){
        coTravellersList.remove(coTraveller)
    }
}

class PictureVideoList() : TripInformation("Pictures and Videos", ""){
    var picturesAndVideosBefore: ArrayList<Uri> = ArrayList()
    var picturesAndVideosFrom: ArrayList<Uri> = ArrayList()

    fun addPictureVideo(pictureOrVideo: Uri, type: PictureVideoType){
        if(type == PictureVideoType.taken_before_trip)
        {
            picturesAndVideosBefore.add(pictureOrVideo)
        }
        else
        {
            picturesAndVideosFrom.add(pictureOrVideo)
        }
    }

    fun getPicturesVideosList(beforeOrFromType: PictureVideoType): ArrayList<Uri>{
        var returnList: ArrayList<Uri> = ArrayList()
        if(beforeOrFromType == PictureVideoType.taken_before_trip)
        {
            returnList = picturesAndVideosBefore
        }
        else
        {
            returnList = picturesAndVideosFrom
        }
        return returnList
    }

    fun deletePictureOrVideo(toDelete : Uri?, type : PictureVideoType? = null)
    {
        if(type == PictureVideoType.taken_before_trip || type == null)
        {
            picturesAndVideosBefore.forEach {
                if(it.equals(toDelete))
                {
                    picturesAndVideosBefore.remove(it)
                    return
                }
            }
        }
        if(type == PictureVideoType.taken_during_trip || type == null)
        {
            picturesAndVideosFrom.forEach{
                if(it.equals(toDelete))
                {
                    picturesAndVideosFrom.remove(it)
                    return
                }
            }
        }
    }
}

class TemplateTripinfo(name: String) : TripInformation(name , "")


class TripDestination(name : String, value: String) : TripInformation(name, value)

