package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Comment : AbstractModel() {

    @SerializedName("commentStatus")
    @Expose
    var commentStatus:String? = null

    @SerializedName("commentBy")
    @Expose
    var commentBy:Users? = null

    @SerializedName("commentTweet")
    @Expose
    var commentTweet:Tweet? = null
}