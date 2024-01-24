package com.example.woc_multimediahub

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.woc_multimediahub.databinding.ActivityMusicBinding
import com.gauravk.audiovisualizer.visualizer.BarVisualizer
import kotlinx.coroutines.Runnable

class musicActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var handler:Handler
    private var title:TextView?=null
    private var backBtn:ImageView?=null
    private var pauseplay:ImageView?=null
    private var nextB:ImageView?=null
    private var prevB:ImageView?=null
    private var musicIcon:ImageView?=null
    private var forward: ImageView?=null
    private var rewind: ImageView?=null
    private var currentTime:TextView?=null
    private var totalTime:TextView?=null
    private lateinit var binding: ActivityMusicBinding
    private var songsList:ArrayList<music>?=null
    private var songName:String?=null
    private var seekBar:SeekBar?=null
    private var duration:Int?=null
    private lateinit var barVisualizer:BarVisualizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = binding.songTitle
        backBtn= binding.AudioBack
        pauseplay = binding.pauseplayAudio
        nextB = binding.nextAudio
        prevB = binding.prevAudio
        musicIcon = binding.musicIcon
        forward = binding.ForwardAudio
        rewind = binding.RewindAudio
        seekBar = binding.SeekBar
        currentTime=binding.currentPosition
        totalTime=binding.totalDuration
        barVisualizer = binding.wave
        handler= Handler()
        player = ExoPlayer.Builder(this).build()


        songsList = intent.getParcelableArrayListExtra("list")
        songName = intent.getStringExtra("songname")
        title!!.text = songName

        var position = intent.getIntExtra("path", 0)
        val size = intent.getIntExtra("size", 101)

        val filePath = songsList!![position].musicPath
        val MediaItem = MediaItem.fromUri(filePath!!)
        player.setMediaItem(MediaItem)
        player.prepare()
        player.play()


            pauseplay!!.setOnClickListener {
                if (player.isPlaying) {
                    pauseplay!!.setImageResource(R.drawable.playblack)
                    player.pause()
                } else {
                    pauseplay!!.setImageResource(R.drawable.pauseblack)
                    player.play()
                }
            }


            prevB!!.setOnClickListener{
                pauseplay!!.setImageResource(R.drawable.pauseblack)
                player.stop()
                position = if ((position!! - 1) < 0)
                    (size - 1)
                else
                    (position!! - 1)
                val filePath = songsList!![position].musicPath
                title!!.text= songsList!![position].musicName
                val MediaItem = androidx.media3.common.MediaItem.fromUri(filePath!!)
                player.setMediaItem(MediaItem)
                player.prepare()
                player.play()
            }

            nextB!!.setOnClickListener{
                pauseplay!!.setImageResource(R.drawable.pauseblack)
                player.stop()
               position = if ((position!!) < size - 1)
                    (position!! + 1)
                else
                    0
                val filePath = songsList!![position].musicPath
                title!!.text= songsList!![position].musicName
                val MediaItem = androidx.media3.common.MediaItem.fromUri(filePath!!)
                player.setMediaItem(MediaItem)
                player.prepare()
                player.play()
            }


        seekBar!!.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
               if (seekBar != null) {
                    player.seekTo(seekBar.progress.toLong())
                }
            }

        })

        var audioSessionId:Int= player.audioSessionId
        if (audioSessionId != -1){
            barVisualizer.setAudioSessionId(audioSessionId)
        }

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

        player.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState== Player.STATE_ENDED){
                    nextB!!.performClick()
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                duration = player.duration.toInt()
                seekBar!!.max = duration!!
                totalTime!!.text=Time(duration!!)
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                var currentPosition = player.currentPosition.toInt()
                seekBar!!.progress = currentPosition
                currentTime?.text= Time(currentPosition)
            }
        })

        var handler = Handler(Looper.getMainLooper())
        handler.post(object :java.lang.Runnable{
            override fun run() {
                var currentPosition = player.currentPosition.toInt()
                seekBar!!.progress = currentPosition
                currentTime?.text= Time(currentPosition)
                handler.postDelayed(this,1000L)
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
        if (barVisualizer != null)
            barVisualizer.release()
        player.release()
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