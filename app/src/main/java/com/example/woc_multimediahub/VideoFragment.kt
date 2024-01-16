package com.example.woc_multimediahub

import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class VideoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var videoRecycler: RecyclerView?=null
    private var allVideos:ArrayList<video>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VideoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoRecycler=view.findViewById(R.id.music_recycler)
        videoRecycler?.layoutManager = GridLayoutManager(context,3)
        videoRecycler?.setHasFixedSize(true)

        allVideos= ArrayList()
        if(allVideos!!.isEmpty())
        {
            allVideos = allVideos()

            videoRecycler?.adapter=VideoAdapter(context, allVideos!!)
        }



    }

    private fun allVideos():ArrayList<video>?{
        val videofiles= ArrayList<video>()
        val allMusicUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val proj = arrayOf(
            MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION
        )
        val cursor =
            this@VideoFragment.context?.contentResolver?.query(allMusicUri,proj,null,null,null)
        try {
            cursor!!.moveToFirst()
            do {
                val video = video()
                video.videoPath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                video.videoDuration =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                video.videoName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                videofiles.add(video)
            } while (cursor.moveToNext())
            cursor.close()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        return videofiles

    }


}