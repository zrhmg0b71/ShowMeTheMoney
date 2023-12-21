package com.example.showmethemoneyproject

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.DateFormatSymbols
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.showmethemoneyproject.databinding.ActivityInputBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class InputActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var foodbalance = 0
    private var carbalance = 0
    private var edubalance = 0
    private var homebalance = 0
    private var savingbalance = 0
    private var hobbybalance = 0
    private var cafebalance = 0
    private var accountbalance = 0
    private var etcbalance = 0


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

        //setupTextWatchers()
        //updateResult()

        // DB에 currentTime 변수명으로 현재 시간 가져가시면 됩니다.
        var currentTime = getCurrentTime()

        binding.calendarBtn.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month]
                binding.setday.text = dayOfMonth.toString()
                updateResult(year, month)
                setupTextWatchers(year, month)
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

    private fun setupTextWatchers(year: Int, month: Int) {
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
                    updateResult(year, month)
                }
            })
        }
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

    private fun updateResult(year: Int, month: Int) {
        val foodCost: Int = if (foodEditText.text.isNotEmpty()) foodEditText.text.toString().toInt() else 0
        val carCost: Int = if (carEditText.text.isNotEmpty()) carEditText.text.toString().toInt() else 0
        val eduCost: Int = if (eduEditText.text.isNotEmpty()) eduEditText.text.toString().toInt() else 0
        val homeCost: Int = if (homeEditText.text.isNotEmpty()) homeEditText.text.toString().toInt() else 0
        val savingCost: Int = if (savingEditText.text.isNotEmpty()) savingEditText.text.toString().toInt() else 0
        val hobbyCost: Int = if (hobbyEditText.text.isNotEmpty()) hobbyEditText.text.toString().toInt() else 0
        val cafeCost: Int = if (cafeEditText.text.isNotEmpty()) cafeEditText.text.toString().toInt() else 0
        val accountCost: Int = if (accountEditText.text.isNotEmpty()) accountEditText.text.toString().toInt() else 0
        val etcCost: Int = if (etcEditText.text.isNotEmpty()) etcEditText.text.toString().toInt() else 0

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        // Firebase 초기화
        val currentTimetable = (year.toString() + (month+1).toString())
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}")
        Log.d("test", currentTimetable)

// 나중에 사용할 데이터를 담을 변수
        val targetKeys = arrayOf("food", "car", "edu", "home", "saving", "hobby", "cafe", "account", "etc")

// 데이터 읽기
        reference.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터를 읽어오는 로직을 여기에 작성
                if (dataSnapshot.exists()) {
                    for (key in targetKeys) {
                        val data = dataSnapshot.child("balance").child(key).value

                        //가져온 데이터를 Int로 변환하고 해당 변수에 저장
                        when (key) {
                            "food" -> foodbalance = data.toString().toInt()
                            "car" -> carbalance = data.toString().toInt()
                            "edu" -> edubalance = data.toString().toInt()
                            "home" -> homebalance = data.toString().toInt()
                            "saving" -> savingbalance = data.toString().toInt()
                            "hobby" -> hobbybalance = data.toString().toInt()
                            "cafe" -> cafebalance = data.toString().toInt()
                            "account" -> accountbalance = data.toString().toInt()
                            "etc" -> etcbalance = data.toString().toInt()
                        }
                    }

                    //잔액을 텍스트뷰에 표시
                    val numberOfDaysInMonth = YearMonth.of(year, month).lengthOfMonth()
                    updateTextViews(numberOfDaysInMonth)

                    // 각 항목의 비용을 더해 잔액에서 차감
                    foodbalance -= foodCost
                    carbalance -= carCost
                    edubalance -= eduCost
                    homebalance -= homeCost
                    savingbalance -= savingCost
                    hobbybalance -= hobbyCost
                    cafebalance -= cafeCost
                    accountbalance -= accountCost
                    etcbalance -= etcCost

                    val totalCost = foodCost + carCost + eduCost + homeCost + savingCost + hobbyCost + cafeCost + accountCost + etcCost

                                        // 각 항목을 데이터베이스에 저장
                    val saveBtn = findViewById<Button>(R.id.accept)
                    saveBtn.setOnClickListener {
                        auth = Firebase.auth

                        val currentTimetable = (year.toString() + (month + 1).toString())
                        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                        val balRef: DatabaseReference =
                            database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}/balance")

                        for (key in targetKeys) {
                            val balanceValue = when (key) {
                                "food" -> foodbalance
                                "car" -> carbalance
                                "edu" -> edubalance
                                "home" -> homebalance
                                "saving" -> savingbalance
                                "hobby" -> hobbybalance
                                "cafe" -> cafebalance
                                "account" -> accountbalance
                                "etc" -> etcbalance
                                else -> 0 // 기본값 설정 또는 예외 처리
                            }

                            balRef.child(key).setValue(balanceValue)
                        }

                        val costRef: DatabaseReference =
                            database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}/spent")

                        for (key in targetKeys) {
                            val spentValue = when (key) {
                                "food" -> foodCost
                                "car" -> carCost
                                "edu" -> eduCost
                                "home" -> homeCost
                                "saving" -> savingCost
                                "hobby" -> hobbyCost
                                "cafe" -> cafeCost
                                "account" -> accountCost
                                "etc" -> etcCost
                                else -> 0 // 기본값 설정 또는 예외 처리
                            }

                            costRef.child(key).setValue(spentValue)
                        }

                        costRef.child("totalspend").setValue(totalCost)

                        //잔액을 텍스트뷰에 표시
                        updateTextViews(numberOfDaysInMonth)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리 로직을 여기에 작성
                println("Error: ${databaseError.message}")
            }
        })

    }

    private fun updateTextViews(days: Int) {
        // 잔액을 텍스트뷰에 표시
        calculationFoodTextView.text = "잔액: ${String.format("%d", foodbalance/days)}"
        calculationCarTextView.text = "잔액: ${String.format("%d", carbalance/days)}"
        calculationEduTextView.text = "잔액: ${String.format("%d", edubalance/days)}"
        calculationHomeTextView.text = "잔액: ${String.format("%d", homebalance/days)}"
        calculationSavingTextView.text = "잔액: ${String.format("%d", savingbalance/days)}"
        calculationHobbyTextView.text = "잔액: ${String.format("%d", hobbybalance/days)}"
        calculationCafeTextView.text = "잔액: ${String.format("%d", cafebalance/days)}"
        calculationAccountTextView.text = "잔액: ${String.format("%d", accountbalance/days)}"
        calculationEtcTextView.text = "잔액: ${String.format("%d", etcbalance/days)}"
    }
}