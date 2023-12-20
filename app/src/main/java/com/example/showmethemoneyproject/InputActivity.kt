package com.example.showmethemoneyproject

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.text.DateFormatSymbols
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputBinding
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityInputBinding
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

        binding.calendarBtn.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols().months[month]
                binding.setday.text = dayOfMonth.toString()
            }
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