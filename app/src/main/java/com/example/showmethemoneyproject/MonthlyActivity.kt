package com.example.showmethemoneyproject

import TextDecorator
import android.app.AlertDialog
import android.content.Intent
import android.icu.text.DateFormatSymbols
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.example.showmethemoneyproject.databinding.ActivityMonthlyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import java.util.Calendar


class MonthlyActivity : AppCompatActivity() {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    private lateinit var auth: FirebaseAuth

    val usageDataList = mutableListOf<UsageDataModel>()
    private lateinit var binding: ActivityMonthlyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

//        val database = Firebase.database
//        val myRef = database.getReference("usageData")
        val listView = binding.usageList
        val adapter_list = ListViewAdapter(usageDataList)
        listView.adapter = adapter_list

        //DB에 입력된 유저 정보 저장
        val database = Firebase.database
        val usageDataTable = (year.toString() + "_" + (month+1).toString() + "_" + day.toString())
        val myRef = database.getReference("usageData/${auth.currentUser!!.uid}/${usageDataTable}")


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {  //데이터베이스에 있는 데이터들을 모두 snapshot이라는 변수에 저장

                for (dataModel in snapshot.children){        //snapshot에 있는 데이터들을 하나하나 꺼냄
                    Log.d("Data", dataModel.toString())
                    usageDataList.add(dataModel.getValue(UsageDataModel::class.java)!!)  //데이터모델에 snapshot에서 꺼낸 데이터 넣어주기
                }
                adapter_list.notifyDataSetChanged()// 데이터모델에 값이 다 들어가고 나면 어댑터를 새로 만들어 줘
                //     = 데이터가 새로 들어오면 리스트뷰에 데이터를 새롭게 넣어 줘
                Log.d("DataModel", usageDataList.toString())  //잘 추가됐는지 확인용 로그
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
                R.id.footer_mypage -> startActivity(intentMyPage)
            }
            true
        }

        binding.calendarBtn.setOnClickListener {
            showCustomDatePickerDialog { year, month ->
                val selectedDate = CalendarDay.from(year, month, 1)
                binding.calendarView.currentDate = selectedDate

                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols().months[month-1]
            }
        }
        //하단 달력 날짜 선택 시 상세 거래 내역 반영
        binding.calendarView.setOnDateChangedListener(object : OnDateSelectedListener {
            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                updateDetailText(date)
                binding.setyear.text = date.year.toString()
                binding.setmonth.text = DateFormatSymbols().months[date.month-1]
            }
        })
        val currentDate = CalendarDay.today()
        updateDetailText(currentDate)

        binding.calendarView.addDecorator(TextDecorator(this))

    }
    private fun updateDetailText(selectedDate: CalendarDay) {
        binding.detailText.text = "${selectedDate.month}월 ${selectedDate.day}일 상세 거래 내역"
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