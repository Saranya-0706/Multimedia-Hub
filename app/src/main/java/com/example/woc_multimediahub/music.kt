package com.example.woc_multimediahub

class music {
    var musicPath:String?=null
    var musicName:String?=null
    var musicDuration:String?=null

    constructor(musicPath: String?, musicName: String?, musicDuration: String?) {
        this.musicPath = musicPath
        this.musicName = musicName
        this.musicDuration = musicDuration
    }

    constructor()
    {}

}