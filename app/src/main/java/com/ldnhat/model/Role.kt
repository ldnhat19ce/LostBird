package com.ldnhat.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Role : AbstractModel() {

    @SerializedName("roleName")
    @Expose
    var roleName:String? = null

    @SerializedName("roleCode")
    @Expose
    var roleCode:String? = null
}