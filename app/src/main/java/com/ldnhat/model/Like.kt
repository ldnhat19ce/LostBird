package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Like : AbstractModel() {

    @SerializedName("userId")
    @Expose
    var userId:Int? = null

    @SerializedName("tweetId")
    @Expose
    var tweetId:Int?=  null
}