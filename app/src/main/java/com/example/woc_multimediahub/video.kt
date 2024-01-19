package com.example.woc_multimediahub

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

class video():Parcelable {
    var videoPath:String?=null
    var videoName:String?=null
    var videoDuration:String?=null

    constructor(parcel: Parcel) : this() {
        videoPath = parcel.readString()
        videoName = parcel.readString()
        videoDuration = parcel.readString()
    }


    constructor(videoPath: String?, videoName: String?, videoDuration: String?) : this() {
        this.videoPath = videoPath
        this.videoName = videoName
        this.videoDuration = videoDuration
    }

    fun Time(duration:Int):String{
        var time :String=""
        var min = duration/1000/60
        var sec = duration/1000%60
        time = time + min+":"
        if(sec<10){
            time+="0"}
        time+=sec
        return time
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoPath)
        parcel.writeString(videoName)
        parcel.writeString(videoDuration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<video> {
        override fun createFromParcel(parcel: Parcel): video {
            return video(parcel)
        }

        override fun newArray(size: Int): Array<video?> {
            return arrayOfNulls(size)
        }
    }

}