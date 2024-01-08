package com.example.woc_multimediahub

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woc_multimediahub.databinding.FragmentPdfBinding
import kotlin.collections.ArrayList

class PdfFragment : Fragment(R.layout.fragment_pdf) {

        private var pdfRecycler:RecyclerView?=null
        private var allFiles:ArrayList<pdf>?=null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pdf, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding = FragmentPdfBinding.inflate(layoutInflater)
        pdfRecycler = view.findViewById(R.id.recycler_pdf)
        val layoutManager = LinearLayoutManager(context)
        pdfRecycler?.layoutManager = layoutManager

        allFiles= ArrayList()
        if(allFiles!!.isEmpty())
        {
            allFiles = pdffiles()

            pdfRecycler?.adapter=MyAdapter(context, allFiles!!)
        }

    }


        private fun pdffiles():ArrayList<pdf>? {
            val pdfFiles = ArrayList<pdf>()
            val allFilesUri = MediaStore.Files.getContentUri("pdf")
            val proj =
                arrayOf(
                    MediaStore.Files.FileColumns.DATA,
                    MediaStore.Files.FileColumns.DISPLAY_NAME
                )
            val mime = MediaStore.Files.FileColumns.MIME_TYPE + "=?"
            val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
            val args = arrayOf(mimetype)

            val cursor = this@PdfFragment.context?.contentResolver?.query(allFilesUri,
                proj, mime, args, null)

            try {
                cursor!!.moveToFirst()
                do{
                    val file= pdf()
                    file.pdfPath=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
                    file.pdfName=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                    pdfFiles.add(file)
                }while (cursor.moveToNext())
                cursor.close()
            }catch (e:Exception)
            {
                e.printStackTrace()
            }

            return pdfFiles
        }

    }
