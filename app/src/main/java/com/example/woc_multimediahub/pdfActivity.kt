package com.example.woc_multimediahub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woc_multimediahub.R.*
import com.example.woc_multimediahub.databinding.ActivityPdfBinding
import com.example.woc_multimediahub.databinding.ActivityVideoBinding
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class pdfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pdfPath = intent.getStringExtra("path")
        val pdfName = intent.getStringExtra("name")

        supportActionBar?.title = pdfName
        var pdfView :PDFView = binding.PDFviewer
        var file = File(pdfPath)
        pdfView.fromFile(file)
            .enableSwipe(true)
            .enableDoubletap(true)
            .defaultPage(0)
            .load()

    }
}