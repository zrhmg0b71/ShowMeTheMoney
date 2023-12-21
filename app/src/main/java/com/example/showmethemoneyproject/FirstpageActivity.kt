package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.showmethemoneyproject.databinding.ActivityFirstpageBinding
import com.example.showmethemoneyproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class FirstpageActivity : AppCompatActivity() {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    private lateinit var auth: FirebaseAuth

    val usageDataList = mutableListOf<UsageDataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirstpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        val database = Firebase.database
        val usageDataTable = (year.toString() + "_" + (month+1).toString() + "_" + day.toString())
        val myRef = database.getReference("usageData/${auth.currentUser!!.uid}/${usageDataTable}")

        val listView = binding.usageList
        val adapter_list = ListViewAdapter(usageDataList)



        listView.adapter = adapter_list

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

        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        val intentInputPage = Intent(this, InputActivity::class.java)
        binding.goToInput.setOnClickListener{startActivity(intentInputPage)}

        val intentSavingPlanPage = Intent(this, SavingPlanActivity::class.java)
        binding.today.setOnClickListener{startActivity(intentSavingPlanPage)}

        val intentMonthlyPage = Intent(this, MonthlyActivity::class.java)
//        binding.goToMonthly.setOnClickListener{startActivity(intentMonthlyPage)}
//        binding.usageList.setOnClickListener{startActivity(intentMonthlyPage)}
        binding.goToMonthly2.setOnClickListener{startActivity(intentMonthlyPage)}

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
}