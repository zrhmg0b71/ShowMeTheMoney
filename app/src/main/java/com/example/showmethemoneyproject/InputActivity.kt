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
import com.example.input.databinding.ActivityInputBinding
import com.example.showmethemoney.R
import java.util.Calendar
import java.util.Locale

class InputActivity : AppCompatActivity() {
    private var foodbalance = 800000
    private var homebalance = 800000
    private var studybalance = 800000
    private var savebalance = 800000

    private lateinit var foodEditText: EditText
    private lateinit var homeEditText: EditText
    private lateinit var studyEditText: EditText
    private lateinit var saveEditText: EditText
    private lateinit var calculationFoodTextView: TextView
    private lateinit var calculationHomeTextView: TextView
    private lateinit var calculationStudyTextView: TextView
    private lateinit var calculationSaveTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodEditText = findViewById(R.id.food)
        homeEditText = findViewById(R.id.home)
        studyEditText = findViewById(R.id.study)
        saveEditText= findViewById(R.id.save)
        calculationFoodTextView = findViewById(R.id.calculateFood)
        calculationHomeTextView = findViewById(R.id.calculateHome)
        calculationStudyTextView = findViewById(R.id.calculateStudy)
        calculationSaveTextView = findViewById(R.id.calculateSavings)


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
        val editTextList = listOf(foodEditText, homeEditText, studyEditText, saveEditText)

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
        val homeCost: Int = if (homeEditText.text.isNotEmpty()) homeEditText.text.toString().toInt() else 0
        val studyCost: Int = if (studyEditText.text.isNotEmpty()) studyEditText.text.toString().toInt() else 0
        val saveCost: Int = if (saveEditText.text.isNotEmpty()) saveEditText.text.toString().toInt() else 0

        // 각 항목의 비용을 더해 잔액에서 차감
        foodbalance = 800000 - foodCost
        homebalance = 800000- homeCost
        studybalance = 800000 - studyCost
        savebalance = 800000 - saveCost

        // 잔액을 텍스트뷰에 표시
        calculationFoodTextView.text = "잔액: ${String.format("%d", foodbalance)}"
        calculationHomeTextView.text = "잔액: ${String.format("%d", homebalance)}"
        calculationStudyTextView.text = "잔액: ${String.format("%d", studybalance)}"
        calculationSaveTextView.text = "잔액: ${String.format("%d", savebalance)}"

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