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
import android.widget.Toast
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


class InputActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val calendar = Calendar.getInstance()
    val defaultYear = calendar.get(Calendar.YEAR)
    val defaultMonth = calendar.get(Calendar.MONTH)
    val defaultDay = calendar.get(Calendar.DAY_OF_MONTH)

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

        //updateResult(defaultYear, defaultMonth)

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

        foodPayEditText = findViewById(R.id.foodPay)
        carPayEditText = findViewById(R.id.carPay)
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
        //var currentTime = getCurrentTime()
        var currentInfo = getCurrentDay()
        binding.setyear.text = currentInfo[0]
        binding.setmonth.text = currentInfo[1]
        binding.setday.text = currentInfo[2]

        binding.calendarBtn.setOnClickListener {
            showDatePickerDialog { year, month, dayOfMonth ->
                binding.setyear.text = year.toString()
                binding.setmonth.text = DateFormatSymbols(Locale.ENGLISH).months[month]
                binding.setday.text = dayOfMonth.toString()
                updateResult(year, month, dayOfMonth)
                setupTextWatchers(year, month, dayOfMonth)
            }
        }

        binding.showbalance.setOnClickListener {
            updateResult(defaultYear, defaultMonth, defaultDay)
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

    private fun getCurrentDay(): Array<String> {
        val currentYear: Int
        val currentMonth: Int
        val currentDay: Int

        if (Build.VERSION.SDK_INT >= 26) {
            val current = LocalDate.now()
            currentYear = current.year
            currentMonth = current.monthValue
            currentDay = current.dayOfMonth
        } else {
            val current = Calendar.getInstance()
            currentYear = current.get(Calendar.YEAR)
            currentMonth = current.get(Calendar.MONTH) + 1
            currentDay = current.get(Calendar.DAY_OF_MONTH)
        }

        val monthsInEnglish = DateFormatSymbols(Locale.ENGLISH).months
        return arrayOf(currentYear.toString(), monthsInEnglish[currentMonth - 1], currentDay.toString())
    }


    private fun setupTextWatchers(year: Int, month: Int, day: Int) {
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
                    updateResult(year, month, day)
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

    private fun updateResult(year: Int = defaultYear, month: Int = defaultMonth, day: Int = defaultMonth) {

        val foodCost: Int = if (foodEditText.text.isNotEmpty()) foodEditText.text.toString().toInt() else 0
        val carCost: Int = if (carEditText.text.isNotEmpty()) carEditText.text.toString().toInt() else 0
        val eduCost: Int = if (eduEditText.text.isNotEmpty()) eduEditText.text.toString().toInt() else 0
        val homeCost: Int = if (homeEditText.text.isNotEmpty()) homeEditText.text.toString().toInt() else 0
        val savingCost: Int = if (savingEditText.text.isNotEmpty()) savingEditText.text.toString().toInt() else 0
        val hobbyCost: Int = if (hobbyEditText.text.isNotEmpty()) hobbyEditText.text.toString().toInt() else 0
        val cafeCost: Int = if (cafeEditText.text.isNotEmpty()) cafeEditText.text.toString().toInt() else 0
        val accountCost: Int = if (accountEditText.text.isNotEmpty()) accountEditText.text.toString().toInt() else 0
        val etcCost: Int = if (etcEditText.text.isNotEmpty()) etcEditText.text.toString().toInt() else 0

        val foodPlace: String = if (foodPlaceEditText.text.isNotEmpty()) foodPlaceEditText.text.toString() else ""
        val carPlace: String = if (carPlaceEditText.text.isNotEmpty()) carPlaceEditText.text.toString() else ""
        val eduPlace: String = if (eduPlaceEditText.text.isNotEmpty()) eduPlaceEditText.text.toString() else ""
        val homePlace: String = if (homePlaceEditText.text.isNotEmpty()) homePlaceEditText.text.toString() else ""
        val savingPlace: String = if (savingPlaceEditText.text.isNotEmpty()) savingPlaceEditText.text.toString() else ""
        val hobbyPlace: String = if (hobbyPlaceEditText.text.isNotEmpty()) hobbyPlaceEditText.text.toString() else ""
        val cafePlace: String = if (cafePlaceEditText.text.isNotEmpty()) cafePlaceEditText.text.toString() else ""
        val accountPlace: String = if (accountPlaceEditText.text.isNotEmpty()) accountPlaceEditText.text.toString() else ""
        val etcPlace: String = if (etcPlaceEditText.text.isNotEmpty()) etcPlaceEditText.text.toString() else ""

        val foodPay: String = if (foodPayEditText.text.isNotEmpty()) foodPayEditText.text.toString() else ""
        val carPay: String = if (carPayEditText.text.isNotEmpty()) carPayEditText.text.toString() else ""
        val eduPay: String = if (eduPayEditText.text.isNotEmpty()) eduPayEditText.text.toString() else ""
        val homePay: String = if (homePayEditText.text.isNotEmpty()) homePayEditText.text.toString() else ""
        val savingPay: String = if (savingPayEditText.text.isNotEmpty()) savingPayEditText.text.toString() else ""
        val hobbyPay: String = if (hobbyPayEditText.text.isNotEmpty()) hobbyPayEditText.text.toString() else ""
        val cafePay: String = if (cafePayEditText.text.isNotEmpty()) cafePayEditText.text.toString() else ""
        val accountPay: String = if (accountPayEditText.text.isNotEmpty()) accountPayEditText.text.toString() else ""
        val etcPay: String = if (etcPayEditText.text.isNotEmpty()) etcPayEditText.text.toString() else ""

        var spentFood = 0
        var spentCar = 0
        var spentEdu = 0
        var spentHome = 0
        var spentSaving = 0
        var spentHobby = 0
        var spentCafe = 0
        var spentAccount = 0
        var spentEtc = 0

        // 나중에 사용할 데이터를 담을 변수
        val targetKeys = arrayOf("food", "car", "edu", "home", "saving", "hobby", "cafe", "account", "etc")

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth


        //결제 금액
        val currentTimetable = (year.toString() + (month+1).toString())
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = database.getReference("Amount/${auth.currentUser!!.uid}/${currentTimetable}")
        Log.d("test", currentTimetable)


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

                    for (key in targetKeys) {
                        val data = dataSnapshot.child("spent").child(key).value

                        //가져온 데이터를 Int로 변환하고 해당 변수에 저장
                        when (key) {
                            "food" -> spentFood = data.toString().toInt()
                            "car" -> spentCar = data.toString().toInt()
                            "edu" -> spentEdu = data.toString().toInt()
                            "home" -> spentHome = data.toString().toInt()
                            "saving" -> spentSaving = data.toString().toInt()
                            "hobby" -> spentHobby = data.toString().toInt()
                            "cafe" -> spentCafe = data.toString().toInt()
                            "account" -> spentAccount = data.toString().toInt()
                            "etc" -> spentEtc = data.toString().toInt()
                        }
                    }

                    //잔액을 텍스트뷰에 표시
                    updateTextViews()

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

                    spentFood += foodCost
                    spentCar += carCost
                    spentEdu += eduCost
                    spentHome += homeCost
                    spentSaving += savingCost
                    spentHobby += hobbyCost
                    spentCafe += cafeCost
                    spentAccount += accountCost
                    spentEtc += etcCost


                    val totalspent = spentFood + spentCar + spentEdu + spentHome + spentSaving + spentHobby + spentCafe + spentAccount + spentEtc


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
                                "food" -> spentFood
                                "car" -> spentCar
                                "edu" -> spentEdu
                                "home" -> spentHome
                                "saving" -> spentSaving
                                "hobby" -> spentHobby
                                "cafe" -> spentCafe
                                "account" -> spentAccount
                                "etc" -> spentEtc
                                else -> 0 // 기본값 설정 또는 예외 처리
                            }

                            costRef.child(key).setValue(spentValue)
                        }

                        costRef.child("totalspend").setValue(totalspent)

                        //사용처, 결제수단
                        val usageDataTable = (year.toString() + "_" + (month+1).toString() + "_" + day.toString())
                        createUsageData("food", foodCost, foodPlace, foodPay, usageDataTable)
                        createUsageData("car", carCost, carPlace, carPay, usageDataTable)
                        createUsageData("edu", eduCost, eduPlace, eduPay, usageDataTable)
                        createUsageData("home", homeCost, homePlace, homePay, usageDataTable)
                        createUsageData("saving", savingCost, savingPlace, savingPay, usageDataTable)
                        createUsageData("hobby", hobbyCost, hobbyPlace, hobbyPay, usageDataTable)
                        createUsageData("cafe", cafeCost, cafePlace, cafePay, usageDataTable)
                        createUsageData("account", accountCost, accountPlace, accountPay, usageDataTable)
                        createUsageData("etc", etcCost, etcPlace, etcPay, usageDataTable)

                        Toast.makeText(this@InputActivity, "저장되었습니다.", Toast.LENGTH_SHORT).show()

                        //잔액을 텍스트뷰에 표시
                        updateTextViews()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리 로직을 여기에 작성
                println("Error: ${databaseError.message}")
                runOnUiThread {
                    Toast.makeText(this@InputActivity, "실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun createUsageData(key: String, cost: Int, place: String, paymentMethod: String, tablename: String) {

        val currentTime = Date()
        val formatter = SimpleDateFormat("HH:mm")
        val formattedTime = formatter.format(currentTime)

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        //DB 연결
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val usageDataRef = database.getReference("usageData/${auth.currentUser!!.uid}/${tablename}/${place}")

        if (cost != 0 && place.isNotEmpty() && paymentMethod.isNotEmpty()) {
            val usageData = UsageDataModel(formattedTime, place, paymentMethod, cost.toString())
            usageDataRef.setValue(usageData)
        }
    }

    private fun updateTextViews() {
        // 잔액을 텍스트뷰에 표시
        calculationFoodTextView.text = "잔액: ${String.format("%d", foodbalance)} 원"
        calculationCarTextView.text = "잔액: ${String.format("%d", carbalance)} 원"
        calculationEduTextView.text = "잔액: ${String.format("%d", edubalance)} 원"
        calculationHomeTextView.text = "잔액: ${String.format("%d", homebalance)} 원"
        calculationSavingTextView.text = "잔액: ${String.format("%d", savingbalance)} 원"
        calculationHobbyTextView.text = "잔액: ${String.format("%d", hobbybalance)} 원"
        calculationCafeTextView.text = "잔액: ${String.format("%d", cafebalance)} 원"
        calculationAccountTextView.text = "잔액: ${String.format("%d", accountbalance)} 원"
        calculationEtcTextView.text = "잔액: ${String.format("%d", etcbalance)} 원"
    }
}