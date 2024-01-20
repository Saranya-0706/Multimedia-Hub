package com.example.woc_multimediahub

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class music() : Parcelable{
    var musicPath:String?=null
    var musicName:String?=null
    var musicDuration:String?=null
    var artUri :Uri?=null

    constructor(parcel: Parcel) : this() {
        musicPath = parcel.readString()
        musicName = parcel.readString()
        musicDuration = parcel.readString()

    }

    constructor(musicPath: String?, musicName: String?, musicDuration: String?, artUri: Uri?) : this() {
        this.musicPath = musicPath
        this.musicName = musicName
        this.musicDuration = musicDuration
        this.artUri = artUri
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(musicPath)
        parcel.writeString(musicName)
        parcel.writeString(musicDuration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<music> {
        override fun createFromParcel(parcel: Parcel): music {
            return music(parcel)
        }

        override fun newArray(size: Int): Array<music?> {
            return arrayOfNulls(size)
        }
    }

}