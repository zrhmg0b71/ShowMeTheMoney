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

class FirstpageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val usageDataList = mutableListOf<usageDataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirstpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

//        val database = Firebase.database
//        val myRef = database.getReference("usageData")

        val listView = binding.usageList
        val adapter_list = ListViewAdapter(usageDataList)


        //availableAmount : { (전체 한 달 수입) + (짜잘한 수입) - (지금까지 지출(저축,방세,교통비...)) } / { (전체 한 달 길이) - (현재 날짜) }
        var availableAmount = 25000
        var todaysTotal = 13000
        var todaysRemain = 12000

        binding.availableAmount.text = "${availableAmount}원"
        binding.todaysTotal.text = "${todaysTotal}원"
        binding.todaysRemain.text = "${todaysRemain}원"

        listView.adapter = adapter_list

        //DB에 입력된 유저 정보 저장
        val database = Firebase.database
        val userInfoRef = database.getReference("usageData")

        usageDataList.add(usageDataModel("09:00 AM", "스타벅스", "#카드", "-4500원"))
        usageDataList.add(usageDataModel("10:00 AM", "투썸플레이스", "#카드", "-5000원"))
        usageDataList.add(usageDataModel("11:00 AM", "할리스커피", "#카드", "-3500원"))

        //DB가 JSON 트리 형식으로 되어 있음
        //유저 정보를 담은 데이터 클래스를 현재 유저의 uid 아래에 setValue로 넣어주면 JSON형식으로 들어감
        userInfoRef
            .child(auth.currentUser!!.uid)  //setValue()는 추가가 아니라 갱신 방식이라 이거 빼먹으면 다른 유저 정보 날아갈 수 있으니 이거 꼭!!
            .setValue(usageDataList)

//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {  //데이터베이스에 있는 데이터들을 모두 snapshot이라는 변수에 저장
//
//                for (dataModel in snapshot.children){        //snapshot에 있는 데이터들을 하나하나 꺼냄
//                    Log.d("Data", dataModel.toString())
//                    usageDataList.add(dataModel.getValue(usageDataModel::class.java)!!)  //데이터모델에 snapshot에서 꺼낸 데이터 넣어주기
//                }
//                adapter_list.notifyDataSetChanged()// 데이터모델에 값이 다 들어가고 나면 어댑터를 새로 만들어 줘
//                //     = 데이터가 새로 들어오면 리스트뷰에 데이터를 새롭게 넣어 줘
//                Log.d("DataModel", usageDataList.toString())  //잘 추가됐는지 확인용 로그
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

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