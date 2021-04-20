package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tweet : AbstractModel() {

    @SerializedName("tweetStatus")
    @Expose
    var tweetStatus:String? = null

    @SerializedName("userModel")
    @Expose
    var user : Users? = null

    @SerializedName("tweetImage")
    @Expose
    var tweetImage:String? = null


    @SerializedName("likeModel")
    @Expose
    var like:Like? = null
}