package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Trend : AbstractModel(){

    @SerializedName("hashtag")
    @Expose
    var hashtag:String? = null
}