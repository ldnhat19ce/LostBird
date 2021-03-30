package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Users : AbstractModel() {

    @SerializedName("username")
    @Expose
    var username:String? = null

    @SerializedName("email")
    @Expose
    var email:String? = null

    @SerializedName("password")
    @Expose
    var password:String? = null

    @SerializedName("role")
    @Expose
    var role: Role? = null

    @SerializedName("screenName")
    @Expose
    var screenName:String? = null

    @SerializedName("profileImage")
    @Expose
    var profileImage:String? = null

    @SerializedName("profileCover")
    @Expose
    var profileCover:String? = null

    @SerializedName("following")
    @Expose
    var following:Int = 0

    @SerializedName("follower")
    @Expose
    var follower:Int = 0

    @SerializedName("bio")
    @Expose
    var bio:String? = null
}