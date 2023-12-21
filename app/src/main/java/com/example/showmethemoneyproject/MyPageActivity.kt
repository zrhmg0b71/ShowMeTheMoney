package com.example.showmethemoneyproject

import android.app.AlertDialog
import android.content.Intent
import android.icu.text.DateFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar
import java.util.Locale

class MyPageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // 월은 0부터 시작하므로 +1 해줍니다.

    private var selectedYear = calendar.get(Calendar.YEAR)
    private var selectedMonth = calendar.get(Calendar.MONTH)+1

    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        binding.userInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, UserInfo::class.java)
            startActivity(intent)
        }

        binding.setNotification.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, SetNotification::class.java)
            startActivity(intent)
        }

        binding.myAccount.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, MyAccount::class.java)
            startActivity(intent)
        }

        binding.calendar.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,Calendar::class.java)
            startActivity(intent)
        }

        binding.weekResult.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, WeekResult::class.java)
            startActivity(intent)
        }

        binding.monthResult.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, MonthResult::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Logout::class.java)
            startActivity(intent)
        }*/

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        val currentTimetable = (year.toString() + (month + 1).toString())
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userInfoRef: DatabaseReference =
            database.getReference("userInfo/${auth.currentUser!!.uid}")


        userInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val name = dataSnapshot.child("name").value.toString()
                    binding.username.text = name
                } else {
                    binding.username.text = "이름 없는 사용자"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리 로직을 여기에 작성
                println("Error: ${databaseError.message}")
            }
        })

        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)

        binding.navigationView.selectedItemId = R.id.footer_mypage
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
            }
            true
        }

        binding.calendarBtn.setOnClickListener {
            showCustomDatePickerDialog { year, month ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month-1]
                binding.amount.text = "${month}월 총 사용 가능 금액"
            }
        }

        binding.logout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this, "로그아웃 완료", Toast.LENGTH_LONG).show()
        }

        binding.accept.setOnClickListener {
            // FirebaseAuth 인스턴스 초기화
            auth = Firebase.auth

            val currentTimetable = (selectedYear.toString() + (selectedMonth).toString())
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val reference: DatabaseReference = database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}")
            val inputValue = binding.input.text.toString()
            reference.child("balance").child("balance").setValue(inputValue)

            Toast.makeText(this, "사용 금액 설정 완료", Toast.LENGTH_LONG).show()

            binding.input.setText("")
        }
        val currentDate = CalendarDay.today()
        updateDetailText(currentDate)

        binding.cancel.setOnClickListener {
            onBackPressed()
        }
    }
    private fun updateDetailText(selectedDate: CalendarDay) {
        binding.amount.text = "${selectedDate.month}월 총 사용 가능 금액"
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
                selectedYear = yearPicker.value
                selectedMonth = monthPicker.value
                Log.d("test", "selected Year : $selectedYear")
                Log.d("test", "selected Month : $selectedMonth")
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