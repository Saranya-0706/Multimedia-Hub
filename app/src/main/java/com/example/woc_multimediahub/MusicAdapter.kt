package com.example.woc_multimediahub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MusicAdapter(private var context:Context?, private var musicList:ArrayList<music>) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>(){

        class MusicViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
            var musicFileName:TextView?=null
            var musicIcon:ImageView?=null
            init{
                musicFileName=itemView.findViewById(R.id.music_title)
                musicIcon = itemView.findViewById(R.id.music_icon)
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
        val dur = currentMusic.musicDuration
        val artworkUri = currentMusic.artUri
        holder.musicFileName?.text = FileName
        if (artworkUri!= null){
            holder.musicIcon?.setImageURI(artworkUri)
            if (holder.musicIcon?.drawable==null){

                holder.musicIcon?.setImageResource(R.drawable.music_note_2_svgrepo_com)
            }
        }


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