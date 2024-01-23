package com.example.woc_multimediahub

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MusicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var musicRecycler: RecyclerView?=null
    private var allMusic:ArrayList<music>?=null
    private var searchmusic : SearchView?=null
    private var tempList:ArrayList<music>?=null


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
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MusicFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchmusic = view.findViewById(R.id.searchMusic)
        musicRecycler=view.findViewById(R.id.music_recycler)
        musicRecycler?.layoutManager = LinearLayoutManager(context)
        musicRecycler?.setHasFixedSize(true)

        allMusic= ArrayList()
        tempList = ArrayList()
        if(tempList!!.isEmpty())
        {
            allMusic = allMusic()
            tempList = allMusic()

            musicRecycler?.adapter=MusicAdapter(context, tempList!!)
        }

        searchmusic?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempList?.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())

                if (searchText.isNotEmpty()){
                    for (item in allMusic!!)
                    {
                        if (item.musicName?.toLowerCase(Locale.getDefault())!!.contains(searchText))
                        {
                            tempList!!.add(item)
                        }
                    }
                    musicRecycler?.adapter?.notifyDataSetChanged()
                }else{
                    tempList?.clear()
                    for (item in allMusic!!)
                    {
                        tempList!!.add(item)
                    }
                    musicRecycler?.adapter?.notifyDataSetChanged()
                }
                return false
            }

        })

        searchmusic!!.setOnCloseListener{
            for (item in allMusic!!)
            {
                tempList!!.add(item)
            }
            musicRecycler?.adapter?.notifyDataSetChanged()
            false
        }

    }

    private fun allMusic():ArrayList<music>?{
        val musicfiles= ArrayList<music>()
        val allMusicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var countMusic =  0
        var musicSize :Float = 0F
        var totalSize :Float = 0F
        val proj = arrayOf(
            MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.ALBUM_ID,MediaStore.Audio.Media.SIZE
        )
        val cursor =
            this@MusicFragment.context?.contentResolver?.query(allMusicUri,proj,null,null,null)
        try {
            cursor!!.moveToFirst()
            do {
                val music = music()
                music.musicPath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                music.musicDuration =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                music.musicName =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))
                val albumId :Long = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID).toLong()
                music.artUri =
                    ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),albumId)

                musicSize = cursor.getFloat(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                totalSize += musicSize
                countMusic++
                musicfiles.add(music)

            } while (cursor.moveToNext())
            cursor.close()
        }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        ItemCount.CountManager.musicCount = countMusic

        ItemCount.FileSize.musicSize = totalSize
            return musicfiles


    }
}