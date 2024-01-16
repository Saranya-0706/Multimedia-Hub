package com.example.woc_multimediahub

import android.graphics.Bitmap

class video {
    var videoPath:String?=null
    var videoName:String?=null
    var videoDuration:String?=null



    constructor()
    {}

    constructor(videoPath: String?, videoName: String?, videoDuration: String?) {
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

}