package com.example.woc_multimediahub

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.example.woc_multimediahub.databinding.ActivityMusicBinding

class musicActivity : AppCompatActivity() {

    private var title:TextView?=null
    private var currentTime:TextView?=null
    private var totalTime:TextView?=null
    private var seekBar:SeekBar?=null
    private var pauseplay:ImageView?=null
    private var nextB:ImageView?=null
    private var prevB:ImageView?=null
    private var musicIcon:ImageView?=null
    private lateinit var binding: ActivityMusicBinding
    private var songsList:ArrayList<music>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title=binding.songTitle
        currentTime=binding.songStart
        totalTime=binding.songEnd
        seekBar=binding.seekBar
        pauseplay=binding.pauseplay
        nextB=binding.next
        prevB=binding.previous
        musicIcon=binding.musicIcon



    }

}