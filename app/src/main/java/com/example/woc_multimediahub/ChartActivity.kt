package com.example.woc_multimediahub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woc_multimediahub.databinding.ActivityChartBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class ChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pieChart :PieChart = binding.pieChart

        var imageCount = ItemCount.CountManager.imageCount
        var videoCount = ItemCount.CountManager.videoCount
        var musicCount = ItemCount.CountManager.musicCount
        var pdfCount = ItemCount.CountManager.pdfCount

        binding.imageNo.text = String.format("Image files   :   %s",imageCount)
        binding.videoNo.text = String.format("Video files   :   %s",videoCount)
        binding.musicNo.text = String.format("Music files   :   %s",musicCount)
        binding.pdfNo.text = String.format("PDF files     :   %s",pdfCount)


        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(imageCount.toFloat(),"Images"))
        entries.add(PieEntry(videoCount.toFloat(),"Videos"))
        entries.add(PieEntry(musicCount.toFloat(),"Music"))
        entries.add(PieEntry(pdfCount.toFloat(),"PDFs"))

        val dataSet = PieDataSet(entries,"")
        dataSet.colors= listOf(
            getColor(R.color.imageColor),
            getColor(R.color.videoColor),
            getColor(R.color.musicColor),
            getColor(R.color.pdfColor)
            )
        dataSet.valueTextSize = 15F
        dataSet.setDrawValues(false)
        pieChart.setDrawSliceText(false)
        pieChart.data = PieData(dataSet)
        pieChart.setUsePercentValues(false)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.legend.textColor = getColor(R.color.black)
        pieChart.legend.textSize = 13F
        pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.invalidate()

    }

}