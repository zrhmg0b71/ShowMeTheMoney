package com.example.showmethemoneyproject

import android.app.AlertDialog
import android.content.Intent
import android.icu.text.DateFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding
import com.example.showmethemoneyproject.databinding.ActivitySavingPlanBinding
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

class SavingPlanActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavingPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()
        var currentYear = calendar.get(Calendar.YEAR)
        var currentMonth = calendar.get(Calendar.MONTH)

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
            }
        }

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        // Firebase 초기화
        val currentTimetable = (currentYear.toString() + (currentMonth + 1).toString())
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference =
            database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}")

        // 사용한 금액 보여주는 곳
        val targetKeys = arrayOf("food", "car", "edu", "home", "saving", "hobby", "cafe", "account", "etc", "totalspend")
        val spentValues = mutableMapOf<String, Int>()


        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (key in targetKeys) {
                        val spent = dataSnapshot.child("spent").child(key).value

                        when (key) {
                            "food" -> binding.foodspent.text = spent.toString()
                            "car" -> binding.carspent.text = spent.toString()
                            "edu" -> binding.eduspent.text = spent.toString()
                            "home" -> binding.homespent.text = spent.toString()
                            "saving" -> binding.savingspent.text = spent.toString()
                            "hobby" -> binding.hobbyspent.text = spent.toString()
                            "cafe" -> binding.carspent.text = spent.toString()
                            "account" -> binding.accountspent.text = spent.toString()
                            "etc" -> binding.etcspent.text = spent.toString()
                        }
                    }

                    for (key in targetKeys) {
                        val spent = dataSnapshot.child("balance").child(key).value

                        when (key) {
                            "food" -> binding.foodmax.text = spent.toString()
                            "car" -> binding.carmax.text = spent.toString()
                            "edu" -> binding.edumax.text = spent.toString()
                            "home" -> binding.homemax.text = spent.toString()
                            "saving" -> binding.savingmax.text = spent.toString()
                            "hobby" -> binding.hobbymax.text = spent.toString()
                            "cafe" -> binding.carmax.text = spent.toString()
                            "account" -> binding.accountmax.text = spent.toString()
                            "etc" -> binding.etcmax.text = spent.toString()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리 로직을 여기에 작성
                println("Error: ${databaseError.message}")
                runOnUiThread {
                    Toast.makeText(this@SavingPlanActivity, "실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })


        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
                R.id.footer_mypage -> startActivity(intentMyPage)
            }
            true
        }
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