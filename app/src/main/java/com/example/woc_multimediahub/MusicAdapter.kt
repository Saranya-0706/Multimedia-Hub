package com.example.woc_multimediahub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private var context:Context?, private var musicList:ArrayList<music>) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>(){

        class MusicViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            var musicFileName:TextView?=null
            init{
                musicFileName=itemView.findViewById(R.id.music_title)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.music_layout,parent,false)
        return MusicViewHolder(view)
    }
    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentMusic = musicList[position]
        val FileName = currentMusic.musicName
        val filepath = currentMusic.musicPath
        val dur = currentMusic.musicDuration
        holder.musicFileName?.text = FileName

        holder.musicFileName!!.setOnClickListener {
            val intent = Intent(context, musicActivity::class.java)
            intent.putParcelableArrayListExtra("list", musicList)
                .putExtra("songname",FileName)
                .putExtra("path",position)
                .putExtra("duration",dur)
                .putExtra("size",musicList.size)

            context!!.startActivity(intent)

        }
    }


}