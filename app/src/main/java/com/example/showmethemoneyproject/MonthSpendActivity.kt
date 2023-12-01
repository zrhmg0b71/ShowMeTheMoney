package com.example.showmethemoneyproject

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.animate
import com.example.showmethemoneyproject.databinding.ActivityLoginBinding
import com.example.showmethemoneyproject.databinding.ActivityMonthSpendBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS

class MonthSpendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMonthSpendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.monthChart.setUsePercentValues(true)


        // data Set
        val entries = ArrayList<PieEntry>()
        // total 12 sections
        entries.add(PieEntry(10f, "식비"))
        entries.add(PieEntry(10f, "교통"))
        entries.add(PieEntry(10f, "교육"))
        entries.add(PieEntry(10f, "방세"))
        entries.add(PieEntry(10f, "저축"))
        entries.add(PieEntry(10f, "취미•여가•쇼핑"))
        entries.add(PieEntry(5f, "카페•간식"))
        entries.add(PieEntry(5f, "이체"))
        entries.add(PieEntry(5f, "기타"))

        // add a lot of colors
//        val colorsItems = ArrayList<Int>()
//        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
//        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
//        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "")

        pieDataSet.colors = listOf(
            ContextCompat.getColor(this, R.color.chart_blue),
            ContextCompat.getColor(this, R.color.chart_green),
            ContextCompat.getColor(this, R.color.chart_pink),
            ContextCompat.getColor(this, R.color.chart_sky_blue),
            ContextCompat.getColor(this, R.color.chart_gray),
            ContextCompat.getColor(this, R.color.chart_orange),
            ContextCompat.getColor(this, R.color.chart_purple),
            ContextCompat.getColor(this, R.color.chart_red),
            ContextCompat.getColor(this, R.color.chart_yellow)
        )

        pieDataSet.apply {
            setDrawValues(false)
            // colors = colorsItems
//            valueTextColor = Color.BLACK
//            valueTextSize = 16f
        }

        val pieData = PieData(pieDataSet)
        binding.monthChart.apply {
            data = pieData
            description.isEnabled = true
            isRotationEnabled = false
            legend.isEnabled = false
            // centerText = "This is Center"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }
}