package com.example.showmethemoneyproject

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.DateFormatSymbols
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityInputBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class InputActivity : AppCompatActivity() {
    private var foodbalance = 800000
    private var carbalance = 800000
    private var edubalance = 800000
    private var homebalance = 800000
    private var savingbalance = 800000
    private var hobbybalance = 800000
    private var cafebalance = 800000
    private var accountbalance = 800000
    private var etcbalance = 800000


    private lateinit var foodEditText: EditText
    private lateinit var carEditText: EditText
    private lateinit var eduEditText: EditText
    private lateinit var homeEditText: EditText
    private lateinit var savingEditText: EditText
    private lateinit var hobbyEditText: EditText
    private lateinit var cafeEditText: EditText
    private lateinit var accountEditText: EditText
    private lateinit var etcEditText: EditText
    private lateinit var foodPlaceEditText: EditText
    private lateinit var carPlaceEditText: EditText
    private lateinit var eduPlaceEditText: EditText
    private lateinit var homePlaceEditText: EditText
    private lateinit var savingPlaceEditText: EditText
    private lateinit var hobbyPlaceEditText: EditText
    private lateinit var cafePlaceEditText: EditText
    private lateinit var accountPlaceEditText: EditText
    private lateinit var etcPlaceEditText: EditText
    private lateinit var foodPayEditText: EditText
    private lateinit var carPayEditText: EditText
    private lateinit var eduPayEditText: EditText
    private lateinit var homePayEditText: EditText
    private lateinit var savingPayEditText: EditText
    private lateinit var hobbyPayEditText: EditText
    private lateinit var cafePayEditText: EditText
    private lateinit var accountPayEditText: EditText
    private lateinit var etcPayEditText: EditText
    private lateinit var calculationFoodTextView: TextView
    private lateinit var calculationCarTextView: TextView
    private lateinit var calculationEduTextView: TextView
    private lateinit var calculationHomeTextView: TextView
    private lateinit var calculationSavingTextView: TextView
    private lateinit var calculationHobbyTextView: TextView
    private lateinit var calculationCafeTextView: TextView
    private lateinit var calculationAccountTextView: TextView
    private lateinit var calculationEtcTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodEditText = findViewById(R.id.food)
        carEditText = findViewById(R.id.car)
        eduEditText = findViewById(R.id.edu)
        homeEditText = findViewById(R.id.home)
        savingEditText = findViewById(R.id.saving)
        hobbyEditText = findViewById(R.id.hobby)
        cafeEditText = findViewById(R.id.cafe)
        accountEditText = findViewById(R.id.account)
        etcEditText = findViewById(R.id.etc)
        foodPlaceEditText = findViewById(R.id.foodPlace)
        carPlaceEditText = findViewById(R.id.carPlace)
        eduPlaceEditText = findViewById(R.id.eduPlace)
        homePlaceEditText = findViewById(R.id.homePlace)
        savingPlaceEditText = findViewById(R.id.savingPlace)
        hobbyPlaceEditText = findViewById(R.id.hobbyPlace)
        cafePlaceEditText = findViewById(R.id.cafePlace)
        accountPlaceEditText = findViewById(R.id.accountPlace)
        etcPlaceEditText = findViewById(R.id.etcPlace)
        foodPayEditText = findViewById(R.id.food)
        carPayEditText = findViewById(R.id.car)
        eduPayEditText = findViewById(R.id.eduPay)
        homePayEditText = findViewById(R.id.homePay)
        savingPayEditText = findViewById(R.id.savingPay)
        hobbyPayEditText = findViewById(R.id.hobbyPay)
        cafePayEditText = findViewById(R.id.cafePay)
        accountPayEditText = findViewById(R.id.accountPay)
        etcPayEditText = findViewById(R.id.etcPay)
        calculationFoodTextView = findViewById(R.id.calculateFood)
        calculationCarTextView = findViewById(R.id.calculateCar)
        calculationEduTextView = findViewById(R.id.calculateEdu)
        calculationHomeTextView = findViewById(R.id.calculateHome)
        calculationSavingTextView = findViewById(R.id.calculateSaving)
        calculationHobbyTextView = findViewById(R.id.calculateHobby)
        calculationCafeTextView = findViewById(R.id.calculateCafe)
        calculationAccountTextView = findViewById(R.id.calculateAccount)
        calculationEtcTextView = findViewById(R.id.calculateEtc)

        setupTextWatchers()
        updateResult()

        // DB에 currentTime 변수명으로 현재 시간 가져가시면 됩니다.
        var currentTime = getCurrentTime()

        binding.calendarBtn.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month]
                binding.setday.text = dayOfMonth.toString()
            }
        }

        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        binding.cancel.setOnClickListener{startActivity(intentFirstPage)}

        binding.navigationView.selectedItemId = R.id.footer_home
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
                R.id.footer_mypage -> startActivity(intentMyPage)
            }
            true
        }
    }

    private fun getCurrentTime() {
        if (Build.VERSION.SDK_INT >= 26) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val formatted = current.format(formatter)
            Log.d("timeTest","현재 시간은 : $formatted")
        } else {
            val current = Calendar.getInstance()
            val hour = current.get(Calendar.HOUR_OF_DAY)
            val minute = current.get(Calendar.MINUTE)
            Log.d("timeTest","현재 시간은 : $hour:$minute")
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
        foodbalance = 800000 - foodCost
        carbalance = 800000 - carCost
        edubalance = 800000- eduCost
        homebalance = 800000- homeCost
        savingbalance = 800000 - savingCost
        hobbybalance = 800000 - hobbyCost
        cafebalance = 800000- cafeCost
        accountbalance = 800000 - accountCost
        etcbalance = 800000 - etcCost

        // 잔액을 텍스트뷰에 표시
        calculationFoodTextView.text = "잔액: ${String.format("%d", foodbalance)}"
        calculationCarTextView.text = "잔액: ${String.format("%d", carbalance)}"
        calculationEduTextView.text = "잔액: ${String.format("%d", edubalance)}"
        calculationHomeTextView.text = "잔액: ${String.format("%d", homebalance)}"
        calculationSavingTextView.text = "잔액: ${String.format("%d", savingbalance)}"
        calculationHobbyTextView.text = "잔액: ${String.format("%d", hobbybalance)}"
        calculationCafeTextView.text = "잔액: ${String.format("%d", cafebalance)}"
        calculationAccountTextView.text = "잔액: ${String.format("%d", accountbalance)}"
        calculationEtcTextView.text = "잔액: ${String.format("%d", etcbalance)}"

    }

    private fun showDatePickerDialog(callback: (year:Int, month:Int, dayOfMonth:Int)->Unit) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // 여기에서 선택된 날짜에 대한 작업을 수행할 수 있습니다.
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                callback.invoke(year,month,dayOfMonth)
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show()
    }
}