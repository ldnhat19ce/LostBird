package com.ldnhat.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Users() : AbstractModel(), Parcelable {

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

    @SerializedName("country")
    @Expose
    var country:String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        username = parcel.readString()
        email = parcel.readString()
        password = parcel.readString()
        screenName = parcel.readString()
        profileImage = parcel.readString()
        profileCover = parcel.readString()
        following = parcel.readInt()
        follower = parcel.readInt()
        bio = parcel.readString()
        country = parcel.readString()
        createDate = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(screenName)
        parcel.writeString(profileImage)
        parcel.writeString(profileCover)
        parcel.writeInt(following)
        parcel.writeInt(follower)
        parcel.writeString(bio)
        parcel.writeString(country)
        parcel.writeLong(createDate!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}