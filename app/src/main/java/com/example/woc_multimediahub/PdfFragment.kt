package com.example.woc_multimediahub

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woc_multimediahub.databinding.FragmentPdfBinding
import java.util.Locale
import kotlin.collections.ArrayList

class PdfFragment : Fragment(R.layout.fragment_pdf) {

        private var pdfRecycler:RecyclerView?=null
        private var allFiles:ArrayList<pdf>?=null
        private var tempList:ArrayList<pdf>?=null
        private var searchview :SearchView?=null

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
        searchview= view.findViewById(R.id.searchPdf)
        pdfRecycler = view.findViewById(R.id.recycler_pdf)
        val layoutManager = LinearLayoutManager(context)
        pdfRecycler?.layoutManager = layoutManager

        allFiles= ArrayList()
        tempList = ArrayList()
        if(tempList!!.isEmpty())
        {
            allFiles= pdffiles()
            tempList = pdffiles()

            pdfRecycler?.adapter=MyAdapter(context, tempList!!)
        }

        searchview?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempList?.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())

                if (searchText.isNotEmpty()){
                    for (item in allFiles!!) {
                        if (item.pdfName?.lowercase(Locale.getDefault())!!.contains(searchText)){
                            tempList?.add(item)
                        }
                    }
                    pdfRecycler?.adapter?.notifyDataSetChanged()

                } else{
                    tempList?.clear()
                    for (item in allFiles!!)
                    {
                        tempList!!.add(item)
                    }
                    pdfRecycler?.adapter?.notifyDataSetChanged()
                }

                return false
            }

        })
     searchview!!.setOnCloseListener {
         for (item in allFiles!!)
         {
             tempList!!.add(item)
         }
         pdfRecycler?.adapter?.notifyDataSetChanged()
         false
     }

    }



        private fun pdffiles():ArrayList<pdf>? {
            val pdfFiles = ArrayList<pdf>()
            val allFilesUri = MediaStore.Files.getContentUri("external")
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
