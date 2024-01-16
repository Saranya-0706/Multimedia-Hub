package com.example.woc_multimediahub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MyAdapter( private var context: Context?, private var pdfList: ArrayList<pdf>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var filename:TextView

        init {
            filename = itemView.findViewById(R.id.textName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.files_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return pdfList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPdf = pdfList[position]
        val fileName= currentPdf.pdfName
        val filePath = currentPdf.pdfPath
        holder.filename.text = fileName

        holder.filename.setOnClickListener {
            val intent = Intent(context,pdfActivity::class.java)
            intent.putExtra("path",filePath)
            intent.putExtra("name",fileName)
            context!!.startActivity(intent)
        }

    }

}