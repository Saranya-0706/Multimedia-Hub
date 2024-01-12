package com.example.woc_multimediahub

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.example.woc_multimediahub.databinding.ActivityMusicBinding
import kotlinx.coroutines.Runnable

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
   var mediaPlayer = MediaPlayer()
    var songName:String?=null
    var position:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = binding.songTitle
        currentTime = binding.songStart
        totalTime = binding.songEnd
        seekBar = binding.seekBar
        pauseplay = binding.pauseplay
        nextB = binding.next
        prevB = binding.previous
        musicIcon = binding.musicIcon


        songsList = intent.getParcelableArrayListExtra("list")
        songName = intent.getStringExtra("songname")
        position = intent.getIntExtra("path", 0)
        title!!.text = songName
        val file = intent.getStringExtra("filepath")
        val size = intent.getIntExtra("size",101)

        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(file)
            mediaPlayer.prepare()
            mediaPlayer.start()

        }catch (e:Exception){
            e.printStackTrace()
        }

        val updateSeekBar = Thread(Runnable {
            var currentPosition = 0
            val totalDuration = mediaPlayer.duration
            while (currentPosition < totalDuration){
                try {
                    Thread.sleep(500)
                    currentPosition = mediaPlayer.currentPosition
                    seekBar!!.progress = currentPosition
                   currentTime!!.text= Time(mediaPlayer.currentPosition)
                    totalTime!!.text = Time(mediaPlayer.duration)

                }
                catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        })
        seekBar!!.max = mediaPlayer.duration
        updateSeekBar.start()



        seekBar!!.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(seekBar!!.progress)
            }

        })

        val handler:Handler?=null
        handler?.postDelayed(object : Runnable{
            override fun run() {

                handler.postDelayed(this,1000)
            }
        },1000)

        pauseplay!!.setOnClickListener{
            if (mediaPlayer.isPlaying) {
                pauseplay!!.setBackgroundResource(R.drawable.play)
                mediaPlayer.pause()
            } else {
                pauseplay!!.setBackgroundResource(R.drawable.pause)
                mediaPlayer.start()
            }
        }


        prevB!!.setOnClickListener(){
            pauseplay!!.setBackgroundResource(R.drawable.pause)
            mediaPlayer.stop()
            mediaPlayer.reset()
            position = if((position!!-1)<0)
                        (size-1)
                       else
                               (position!! -1)
            val filepath = songsList!![position!!].musicPath
            mediaPlayer.setDataSource(filepath)
            songName= songsList!![position!!].musicName
            title!!.text = songName
            mediaPlayer.prepare()
            mediaPlayer.start()
        }

        nextB!!.setOnClickListener(){
            pauseplay!!.setBackgroundResource(R.drawable.pause)
            mediaPlayer.stop()
            mediaPlayer.reset()
            position = if((position!!)<size-1)
                (position!! +1)
            else
                0
            val filepath = songsList!![position!!].musicPath
            mediaPlayer.setDataSource(filepath)
            songName= songsList!![position!!].musicName
            title!!.text = songName
            mediaPlayer.prepare()
            mediaPlayer.start()
        }


    }

    override fun onBackPressed() {
        if(mediaPlayer.isPlaying)
        {
            mediaPlayer.pause()
        }
        super.onBackPressed()
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