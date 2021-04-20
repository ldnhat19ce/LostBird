package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

abstract class AbstractModel {

    @SerializedName("id")
    @Expose
    var id:Int = 0

    @SerializedName("createDate")
    @Expose
    var createDate:Long? = null

    @SerializedName("createBy")
    @Expose
    var createBy:String? = null

    @SerializedName("modifyDate")
    @Expose
    var modifyDate:Timestamp? = null

    @SerializedName("modifyBy")
    @Expose
    var modifyBy:String? = null

    @SerializedName("message")
    @Expose
    var message:String? = null
}