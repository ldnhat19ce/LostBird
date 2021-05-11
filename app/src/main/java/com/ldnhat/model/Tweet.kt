package com.ldnhat.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Tweet() : AbstractModel(), Parcelable {

    @SerializedName("tweetStatus")
    @Expose
    var tweetStatus:String? = null

    @SerializedName("userModel")
    @Expose
    var user : Users? = null

    @SerializedName("tweetImage")
    @Expose
    var tweetImage:String? = null


    @SerializedName("isLike")
    @Expose
    var isLike:Int? = 0

    constructor(parcel: Parcel) : this() {
        tweetStatus = parcel.readString()
        tweetImage = parcel.readString()
        user = parcel.readParcelable(Users::class.java.classLoader)
        isLike = parcel.readInt()
        createDate = parcel.readLong()
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tweetStatus)
        parcel.writeString(tweetImage)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(isLike!!)
        parcel.writeLong(createDate!!)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tweet> {
        override fun createFromParcel(parcel: Parcel): Tweet {
            return Tweet(parcel)
        }

        override fun newArray(size: Int): Array<Tweet?> {
            return arrayOfNulls(size)
        }
    }
}