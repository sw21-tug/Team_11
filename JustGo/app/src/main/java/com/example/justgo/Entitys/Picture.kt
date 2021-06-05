package com.example.justgo.Entitys

import android.net.Uri
import java.io.Serializable

class Picture(var uri: Uri,var type: PictureVideoType):Serializable {
    var uri_:Uri = uri
    var type_:PictureVideoType = type
}