package com.example.woc_multimediahub

import android.content.Context
import android.provider.MediaStore

class ItemCount {
    object CountManager {
        var imageCount: Int = 0
        var videoCount: Int = 0
        var musicCount: Int = 0
        var pdfCount: Int = 0
    }

    object FileSize{

        var imageSize: Float = 0F
        var videoSize: Float = 0F
        var musicSize: Float = 0F
        var pdfSize: Float = 0F
    }
}