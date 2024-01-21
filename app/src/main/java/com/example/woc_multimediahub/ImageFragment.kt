package com.example.woc_multimediahub

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var imageRecycler:RecyclerView?=null
    private var allPics:ArrayList<image>?=null

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
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageRecycler=view.findViewById(R.id.recycler_images)
        imageRecycler?.layoutManager=GridLayoutManager(context, 3)
        imageRecycler?.setHasFixedSize(true)


        allPics= ArrayList()
        if(allPics!!.isEmpty())
        {
            allPics = allImages()

            imageRecycler?.adapter=ImageAdapter(context, allPics!!)
        }
    }

    private fun allImages(): ArrayList<image>? {
          val images = ArrayList<image>()
        var countImg :Int =0
          val allImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
          val proj = arrayOf(MediaStore.Images.ImageColumns.DATA,MediaStore.Images.Media.DISPLAY_NAME)
          val cursor =
              this@ImageFragment.context?.contentResolver?.query(allImgUri,proj,null,null,null)

        try {
            cursor!!.moveToFirst()
            do{
                val image=image()
                image.imagePath=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                countImg++
                images.add(image)
            }while (cursor.moveToNext())
            cursor.close()
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
        ItemCount.CountManager.imageCount =countImg
        return images
    }
}