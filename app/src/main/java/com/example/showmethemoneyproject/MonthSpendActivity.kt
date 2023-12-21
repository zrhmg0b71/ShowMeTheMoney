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
import android.util.Log
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.animate
import androidx.lifecycle.MutableLiveData
import com.example.showmethemoneyproject.databinding.ActivityLoginBinding
import com.example.showmethemoneyproject.databinding.ActivityMonthSpendBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.Calendar
import java.util.Locale

class MonthSpendActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMonthSpendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        var currentYear = calendar.get(Calendar.YEAR)
        var currentMonth = calendar.get(Calendar.MONTH)
        var currentDay = calendar.get(Calendar.DAY_OF_MONTH)

//        setupTextWatchers()
//        updateResult()

        binding.calendarBtn.setOnClickListener {
            showCustomDatePickerDialog { year, month ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month-1]

                // currentYear 및 currentMonth 변수 업데이트
                currentYear = year
                currentMonth = month

                // calendar 변수 업데이트
                calendar.set(Calendar.YEAR, currentYear)
                calendar.set(Calendar.MONTH, currentMonth - 1)

                updateUI(currentYear, currentMonth) { spentValues ->
                    val monthTotal = findViewById<TextView>(R.id.monthsTotal)
                    monthTotal.text = spentValues["totalspend"].toString()
                    val foodTotal = findViewById<TextView>(R.id.foodTotal)
                    foodTotal.text = spentValues["food"].toString()
                    val carTotal = findViewById<TextView>(R.id.carTotal)
                    carTotal.text = spentValues["car"].toString()
                    val eduTotal = findViewById<TextView>(R.id.eduTotal)
                    eduTotal.text = spentValues["edu"].toString()
                    val homeTotal = findViewById<TextView>(R.id.homeTotal)
                    homeTotal.text = spentValues["home"].toString()
                    val savingTotal = findViewById<TextView>(R.id.savingTotal)
                    savingTotal.text = spentValues["saving"].toString()
                    val hobbyTotal = findViewById<TextView>(R.id.hobbyTotal)
                    hobbyTotal.text = spentValues["hobby"].toString()
                    val cafeTotal = findViewById<TextView>(R.id.cafeTotal)
                    cafeTotal.text = spentValues["cafe"].toString()
                    val accountTotal = findViewById<TextView>(R.id.accountTotal)
                    accountTotal.text = spentValues["account"].toString()
                    val etcTotal = findViewById<TextView>(R.id.etcTotal)
                    etcTotal.text = spentValues["etc"].toString()

                }
            }
        }


        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        // Firebase 초기화
        val currentTimetable = (currentYear.toString() + (currentMonth).toString())
        Log.d("test", "$currentYear 과 $currentMonth")
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference =
            database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}/spent")

        val targetKeys = arrayOf("food", "car", "edu", "home", "saving", "hobby", "cafe", "account", "etc", "totalspend")
        val spentValues = mutableMapOf<String, Int>()

        for (key in targetKeys) {
            reference.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val data = dataSnapshot.value
                    spentValues[key] = data.toString().toIntOrNull() ?: 0

                    // spentValues["food"] 로 현재까지 식비소비금액 확인 가능(다른 항목도 마찬가지)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error: ${databaseError.message}")
                }
            })
        }

        var spentTotal = spentValues["totalspend"]?.toString() ?: "0"
        binding.monthsTotal.text = spentTotal


//        binding.foodPercent.text = ((spentValues["food"]?.toFloat() ?: 0f).toString() + "%")
//        binding.carPercent.text = ((spentValues["car"]?.toFloat() ?: 0f).toString() + "%")
//        binding.eduPercent.text = ((spentValues["edu"]?.toFloat() ?: 0f).toString() + "%")
//        binding.homePercent.text = ((spentValues["home"]?.toFloat() ?: 0f).toString() + "%")
//        binding.savingPercent.text = ((spentValues["saving"]?.toFloat() ?: 0f).toString() + "%")
//        binding.hobbyPercent.text = ((spentValues["hobby"]?.toFloat() ?: 0f).toString() + "%")
//        binding.cafePercent.text = ((spentValues["cafe"]?.toFloat() ?: 0f).toString() + "%")
//        binding.accountPercent.text = ((spentValues["account"]?.toFloat() ?: 0f).toString() + "%")
//        binding.etcPercent.text = ((spentValues["etc"]?.toFloat() ?: 0f).toString() + "%")

        binding.foodPercent.text = "10%"
        binding.carPercent.text = "10%"
        binding.eduPercent.text = "20%"
        binding.homePercent.text = "10%"
        binding.savingPercent.text = "10%"
        binding.hobbyPercent.text = "10%"
        binding.cafePercent.text = "10%"
        binding.accountPercent.text = "10%"
        binding.etcPercent.text = "10%"

        binding.foodTotal.text = spentValues["food"]?.toString() ?: "0"
        binding.carTotal.text = spentValues["car"]?.toString() ?: "0"
        binding.eduTotal.text = spentValues["edu"]?.toString() ?: "0"
        binding.homeTotal.text = spentValues["home"]?.toString() ?: "0"
        binding.savingTotal.text = spentValues["saving"]?.toString() ?: "0"
        binding.hobbyTotal.text = spentValues["hobby"]?.toString() ?: "0"
        binding.cafeTotal.text = spentValues["cafe"]?.toString() ?: "0"
        binding.accountTotal.text = spentValues["account"]?.toString() ?: "0"
        binding.etcTotal.text = spentValues["etc"]?.toString() ?: "0"

        // 이 아래부터 차트
        binding.monthChart.setUsePercentValues(true)

        // data Set
        val entries = ArrayList<PieEntry>()
        // total 12 sections
//        entries.add(PieEntry(spentValues["food"]?.toFloat() ?: 0f, "식비"))
//        entries.add(PieEntry(spentValues["car"]?.toFloat() ?: 0f, "교통"))
//        entries.add(PieEntry(spentValues["edu"]?.toFloat() ?: 0f, "교육"))
//        entries.add(PieEntry(spentValues["home"]?.toFloat() ?: 0f, "방세"))
//        entries.add(PieEntry(spentValues["saving"]?.toFloat() ?: 0f, "저축"))
//        entries.add(PieEntry(spentValues["hobby"]?.toFloat() ?: 0f, "취미•여가•쇼핑"))
//        entries.add(PieEntry(spentValues["cafe"]?.toFloat() ?: 0f, "카페•간식"))
//        entries.add(PieEntry(spentValues["account"]?.toFloat() ?: 0f, "이체"))
//        entries.add(PieEntry(spentValues["etc"]?.toFloat() ?: 0f, "기타"))

        // 하드코딩
        entries.add(PieEntry(10f, "식비"))
        entries.add(PieEntry(10f, "교통"))
        entries.add(PieEntry(20f, "교육"))
        entries.add(PieEntry(10f, "방세"))
        entries.add(PieEntry(10f, "저축"))
        entries.add(PieEntry(10f, "취미•여가•쇼핑"))
        entries.add(PieEntry(10f, "카페•간식"))
        entries.add(PieEntry(10f, "이체"))
        entries.add(PieEntry(10f, "기타"))

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

    private fun updateUI(year: Int, month: Int, onUpdated: (Map<String, Int>) -> Unit) {
        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        // Firebase 초기화
        val currentTimetable = (year.toString() + (month).toString())
        Log.d("test", "$year 과 $month")
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference =
            database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}/spent")

        val targetKeys = arrayOf("food", "car", "edu", "home", "saving", "hobby", "cafe", "account", "etc", "totalspend")
        val spentValues = mutableMapOf<String, Int>()

        var count = 0
        for (key in targetKeys) {
            reference.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val data = dataSnapshot.value
                    spentValues[key] = data.toString().toInt()
                    Log.d("test", "Update : $spentValues")

                    count++
                    if (count == targetKeys.size) {
                        onUpdated(spentValues)
                    }
                    // spentValues["food"] 로 현재까지 식비소비금액 확인 가능(다른 항목도 마찬가지)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error: ${databaseError.message}")
                }
            })
        }

        val monthTotal = findViewById<TextView>(R.id.monthsTotal)
        monthTotal.text = spentValues["totalspend"].toString()
        val foodTotal = findViewById<TextView>(R.id.foodTotal)
        foodTotal.text = spentValues["food"].toString()
        val carTotal = findViewById<TextView>(R.id.carTotal)
        carTotal.text = spentValues["car"].toString()
        val eduTotal = findViewById<TextView>(R.id.eduTotal)
        eduTotal.text = spentValues["edu"].toString()
        val homeTotal = findViewById<TextView>(R.id.homeTotal)
        homeTotal.text = spentValues["home"].toString()
        val savingTotal = findViewById<TextView>(R.id.savingTotal)
        savingTotal.text = spentValues["saving"].toString()
        val hobbyTotal = findViewById<TextView>(R.id.hobbyTotal)
        hobbyTotal.text = spentValues["hobby"].toString()
        val cafeTotal = findViewById<TextView>(R.id.cafeTotal)
        cafeTotal.text = spentValues["cafe"].toString()
        val accountTotal = findViewById<TextView>(R.id.accountTotal)
        accountTotal.text = spentValues["account"].toString()
        val etcTotal = findViewById<TextView>(R.id.etcTotal)
        etcTotal.text = spentValues["etc"].toString()

//        val foodPercent = findViewById<TextView>(R.id.carPercent)
//        foodPercent.text = ((spentValues["food"]?.toFloat() ?: 0f).toString() + "%")
//        val carPercent = findViewById<TextView>(R.id.carPercent)
//        carPercent.text = ((spentValues["car"]?.toFloat() ?: 0f).toString() + "%")
//        val eduPercent = findViewById<TextView>(R.id.carPercent)
//        eduPercent.text = ((spentValues["edu"]?.toFloat() ?: 0f).toString() + "%")
//        val homePercent = findViewById<TextView>(R.id.carPercent)
//        homePercent.text = ((spentValues["home"]?.toFloat() ?: 0f).toString() + "%")
//        val savingPercent = findViewById<TextView>(R.id.carPercent)
//        savingPercent.text = ((spentValues["saving"]?.toFloat() ?: 0f).toString() + "%")
//        val hobbyPercent = findViewById<TextView>(R.id.carPercent)
//        hobbyPercent.text = ((spentValues["hobby"]?.toFloat() ?: 0f).toString() + "%")
//        val cafePercent = findViewById<TextView>(R.id.carPercent)
//        cafePercent.text = ((spentValues["cafe"]?.toFloat() ?: 0f).toString() + "%")
//        val accountPercent = findViewById<TextView>(R.id.carPercent)
//        accountPercent.text = ((spentValues["account"]?.toFloat() ?: 0f).toString() + "%")
//        val etcPercent = findViewById<TextView>(R.id.carPercent)
//        etcPercent.text = ((spentValues["etc"]?.toFloat() ?: 0f).toString() + "%")

    }



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