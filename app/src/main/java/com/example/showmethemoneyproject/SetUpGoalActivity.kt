package com.example.showmethemoneyproject

import android.icu.text.DateFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import java.util.Calendar
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivitySetUpGoalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Locale

class SetUpGoalActivity : AppCompatActivity() {
    private var selectedYear = 1970
    private var selectedMonth = 1

    private lateinit var auth: FirebaseAuth

    private var balance = 800000
    private lateinit var foodEditText: EditText
    private lateinit var carEditText: EditText
    private lateinit var eduEditText: EditText
    private lateinit var homeEditText: EditText
    private lateinit var savingEditText: EditText
    private lateinit var hobbyEditText: EditText
    private lateinit var cafeEditText: EditText
    private lateinit var accountEditText: EditText
    private lateinit var etcEditText: EditText
    private lateinit var calculationResultTextView: TextView

    var currentMonth = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySetUpGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val healMemo = mAlertDialog.findViewById<EditText>(R.id.healthMemo)?.text.toString()
        //val foodCost: Int = if (foodEditText.text.isNotEmpty()) foodEditText.text.toString().toInt() else 0

        foodEditText = findViewById(R.id.food)
        carEditText = findViewById(R.id.car)
        eduEditText = findViewById(R.id.edu)
        homeEditText = findViewById(R.id.home)
        savingEditText = findViewById(R.id.saving)
        hobbyEditText = findViewById(R.id.hobby)
        cafeEditText = findViewById(R.id.cafe)
        accountEditText = findViewById(R.id.account)
        etcEditText = findViewById(R.id.etc)
        calculationResultTextView = findViewById(R.id.calculateResult)

        setupTextWatchers()
        updateResult()

        binding.calendarBtn.setOnClickListener {
            showCustomDatePickerDialog { year, month ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month-1]
            }
        }

        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        binding.cancel.setOnClickListener{startActivity(intentFirstPage)}
        binding.navigationView.selectedItemId = R.id.footer_wallet
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
                R.id.footer_mypage -> startActivity(intentMyPage)
            }
            true
        }
    }
    private fun setupTextWatchers() {
        val editTextList = listOf(foodEditText, carEditText, eduEditText, homeEditText,
            savingEditText, hobbyEditText, cafeEditText, accountEditText, etcEditText)

        for (editText in editTextList) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    updateResult()
                }
            })
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
    private fun updateResult() {
        val foodCost: Int = if (foodEditText.text.isNotEmpty()) foodEditText.text.toString().toInt() else 0
        val carCost: Int = if (carEditText.text.isNotEmpty()) carEditText.text.toString().toInt() else 0
        val eduCost: Int = if (eduEditText.text.isNotEmpty()) eduEditText.text.toString().toInt() else 0
        val homeCost: Int = if (homeEditText.text.isNotEmpty()) homeEditText.text.toString().toInt() else 0
        val savingCost: Int = if (savingEditText.text.isNotEmpty()) savingEditText.text.toString().toInt() else 0
        val hobbyCost: Int = if (hobbyEditText.text.isNotEmpty()) hobbyEditText.text.toString().toInt() else 0
        val cafeCost: Int = if (cafeEditText.text.isNotEmpty()) cafeEditText.text.toString().toInt() else 0
        val accountCost: Int = if (accountEditText.text.isNotEmpty()) accountEditText.text.toString().toInt() else 0
        val etcCost: Int = if (etcEditText.text.isNotEmpty()) etcEditText.text.toString().toInt() else 0

        // 각 항목의 비용을 더해 잔액에서 차감
        balance = 800000 - (foodCost + carCost + eduCost + homeCost + savingCost + hobbyCost
                + cafeCost + accountCost + etcCost)
        if(balance <0){
            Toast.makeText(this, "잔액이 부족합니다.", Toast.LENGTH_LONG).show()
        }

        // 잔액을 텍스트뷰에 표시
        calculationResultTextView.text = "잔액: ${String.format("%d", balance)}"



        val saveBtn = findViewById<Button>(R.id.accept)
        saveBtn?.setOnClickListener {

            auth = Firebase.auth
            val database = Firebase.database
            val amountRef = database.getReference("Amount")

//            val database = Firebase.database
//            val userInfoRef = database.getReference("usageData")
//
//            usageDataList.add(usageDataModel("09:00 AM", "스타벅스", "#카드", "-4500원"))
//            usageDataList.add(usageDataModel("10:00 AM", "투썸플레이스", "#카드", "-5000원"))
//            usageDataList.add(usageDataModel("11:00 AM", "할리스커피", "#카드", "-3500원"))
//
//            //DB가 JSON 트리 형식으로 되어 있음
//            //유저 정보를 담은 데이터 클래스를 현재 유저의 uid 아래에 setValue로 넣어주면 JSON형식으로 들어감
//            userInfoRef
//                .child(auth.currentUser!!.uid)  //setValue()는 추가가 아니라 갱신 방식이라 이거 빼먹으면 다른 유저 정보 날아갈 수 있으니 이거 꼭!!
//                .setValue(usageDataList)

            val table = (selectedYear.toString() + selectedMonth.toString())
            val model = AmountModel(balance, foodCost, carCost, eduCost, homeCost, savingCost, hobbyCost, cafeCost, accountCost, etcCost)
            amountRef
                .child(auth.currentUser!!.uid)
                .child(table)
                .setValue(model)
        }
    }



}