package com.example.showmethemoneyproject

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.text.DateFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.NumberPicker
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
import java.util.Calendar
import java.util.Locale

class MonthSpendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMonthSpendBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        setupTextWatchers()
//        updateResult()

        // 월 선택
        binding.calendarBtn.setOnClickListener {
            showCustomDatePickerDialog { year, month ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month-1]
            }
        }

        // 이 아래부터 차트
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
        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        binding.navigationView.selectedItemId = R.id.footer_calendar
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_mypage -> startActivity(intentMyPage)
            }
            true
        }
    }

//    private fun setupTextWatchers() {
//        val editTextList = listOf(foodEditText, carEditText, eduEditText, homeEditText,
//            savingEditText, hobbyEditText, cafeEditText, accountEditText, etcEditText)
//
//        for (editText in editTextList) {
//            editText.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//                override fun afterTextChanged(s: Editable?) {
//                    updateResult()
//                }
//            })
//        }
//    }

    private fun showCustomDatePickerDialog(callback:(year:Int,month:Int)->Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val dialogView = layoutInflater.inflate(R.layout.date_picker, null)
        val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)
        val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)

        // yearPicker와 monthPicker를 초기화하고 설정
        yearPicker.minValue = 1970
        yearPicker.maxValue = 2100
        yearPicker.value = year

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = month

        // 다이얼로그 생성
        val dialog = AlertDialog.Builder(this)
            .setTitle("Select Year and Month")
            .setPositiveButton("OK") { _, _ ->
                // OK 버튼을 눌렀을 때의 동작
                val selectedYear = yearPicker.value
                val selectedMonth = monthPicker.value
                // 선택된 년과 월을 사용하여 원하는 동작 수행
                callback.invoke(selectedYear,selectedMonth)
            }
            .setNegativeButton("Cancel") { _, _ ->
                // Cancel 버튼을 눌렀을 때의 동작
            }
            .setView(dialogView)
            .create()

        // 다이얼로그 보여주기
        dialog.show()
    }
}