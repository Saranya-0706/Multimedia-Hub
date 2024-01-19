package com.example.woc_multimediahub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.MediaController.MediaPlayerControl
import android.widget.SeekBar
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.DefaultTimeBar
import com.example.woc_multimediahub.databinding.ActivityMainBinding
import com.example.woc_multimediahub.databinding.ActivityVideoBinding

class videoActivity : AppCompatActivity() {

     private lateinit var binding: ActivityVideoBinding
    private lateinit var player:ExoPlayer
    private var backBtn: ImageView?=null
    private var pauseplay: ImageView?=null
    private var nextVideo: ImageView?=null
    private var prevVideo: ImageView?=null
    private var forward: ImageView?=null
    private var rewind: ImageView?=null
    private var title: TextView?=null
    private var position: TextView?=null
    private var duration: TextView?=null
    private var videoList :ArrayList<video>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var path = intent.getIntExtra("path",1)
        val size = intent.getIntExtra("size",0)
        videoList = intent.getParcelableArrayListExtra("list")


        player = ExoPlayer.Builder(this).build()
        binding.PlayerView.player = player

        backBtn = binding.PlayerView.findViewById(R.id.ExoBack)
       pauseplay = binding.PlayerView.findViewById(R.id.exo_pause)
         nextVideo = binding.PlayerView.findViewById(R.id.ExoNext)
        prevVideo= binding.PlayerView.findViewById(R.id.ExoPrev)
        forward = binding.PlayerView.findViewById(R.id.ExoForward)
        rewind = binding.PlayerView.findViewById(R.id.ExoRewind)
        title = binding.PlayerView.findViewById(R.id.videoTitle)
        position = binding.PlayerView.findViewById(R.id.exo_position)
        duration = binding.PlayerView.findViewById(R.id.exo_duration)


        title!!.text = intent.getStringExtra("name")
        val filePath = videoList!![path!!].videoPath
        val MediaItem = MediaItem.fromUri(filePath!!)
        player.setMediaItem(MediaItem)
        player.prepare()
        player.play()


        backBtn!!.setOnClickListener {
            finish()
        }

        forward!!.setOnClickListener {
           player.seekTo(player.currentPosition + 10000)
        }

        rewind!!.setOnClickListener {
            var x:Long = player.currentPosition - 10000
            if (x<0){
                player.seekTo(0)
            }
            player.seekTo(player.currentPosition - 10000)
        }

       pauseplay!!.setOnClickListener {
           if (player.isPlaying){
               pauseplay!!.setBackgroundResource(R.drawable.play)
               player.pause()


           }
           else {
               pauseplay!!.setBackgroundResource(R.drawable.pause)
               player.play()
           }
       }

        nextVideo!!.setOnClickListener {
            pauseplay!!.setBackgroundResource(R.drawable.pause)
            player.stop()
            path = if((path!!)<size-1)
                (path!! +1)
            else
                0
            val filepath = videoList!![path!!].videoPath
             val MediaItem = androidx.media3.common.MediaItem.fromUri(filepath!!)
            player.setMediaItem(MediaItem)
            player.prepare()
            player.play()
        }

        prevVideo!!.setOnClickListener {
            pauseplay!!.setBackgroundResource(R.drawable.pause)
            player.stop()
            path = if((path!!-1)<0)
                (size-1)
            else
                (path!! -1)
            val filepath = videoList!![path!!].videoPath
            val MediaItem = androidx.media3.common.MediaItem.fromUri(filepath!!)
            player.setMediaItem(MediaItem)
            player.prepare()
            player.play()
        }

        player.addListener(object :Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState==Player.STATE_ENDED){
                    nextVideo!!.performClick()
                }
            }
        })
        player.playWhenReady =true
    }


    override fun onStart() {
        super.onStart()
        player.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

}