package com.example.woc_multimediahub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class VideoAdapter(private var context: Context?, private var videoList:ArrayList<video>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
        class VideoViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            var videoImage:ImageView?=null
            var durOnLayout:TextView?=null
            init {
                videoImage =itemView.findViewById(R.id.Thumbnail)
                durOnLayout=itemView.findViewById(R.id.durOnLayout)

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.video_layout,parent,false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
       return videoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentVideo = videoList[position]
        val FileName = currentVideo.videoName
        val filepath = currentVideo.videoPath
        val dur = currentVideo.videoDuration

        holder?.videoImage?.let {
            Glide.with(context!!)
                .asBitmap()
                .load(filepath)
                // .options(RequestOptions().placeholder(R.id.video))
                .into(it)
        }

        if (dur != null) {
            holder.durOnLayout?.text = currentVideo.Time(dur.toInt())
        }

        holder.videoImage!!.setOnClickListener{
            val intent = Intent(context,videoActivity::class.java)
              intent.putExtra("path",position)
              intent.putExtra("name",FileName)
              intent.putExtra("size",videoList.size)
              intent.putExtra("filepath",filepath)
              intent.putParcelableArrayListExtra("list", videoList)

            context!!.startActivity(intent)
        }
    }

}