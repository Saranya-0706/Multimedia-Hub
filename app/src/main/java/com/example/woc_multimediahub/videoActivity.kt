package com.example.woc_multimediahub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController.MediaPlayerControl
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.woc_multimediahub.databinding.ActivityMainBinding
import com.example.woc_multimediahub.databinding.ActivityVideoBinding

class videoActivity : AppCompatActivity() {

     private lateinit var binding: ActivityVideoBinding
    lateinit var player:ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val path = intent.getStringExtra("path")

        player = ExoPlayer.Builder(this).build()
        binding.PlayerView.player = player


        val MediaItem = MediaItem.fromUri(path!!)
        player.setMediaItem(MediaItem)
        player.prepare()
        player.play()

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