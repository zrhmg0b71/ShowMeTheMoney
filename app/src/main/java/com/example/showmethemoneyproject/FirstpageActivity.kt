package com.example.showmethemoneyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.showmethemoneyproject.databinding.ActivityFirstpageBinding
import com.example.showmethemoneyproject.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirstpageActivity : AppCompatActivity() {

    val usageDataList = mutableListOf<usageDataModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirstpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        usageDataList.add(usageDataModel("09:00 AM", "스타벅스", "#카드", "-4500원"))
        usageDataList.add(usageDataModel("10:00 AM", "투썸플레이스", "#카드", "-5000원"))
        usageDataList.add(usageDataModel("11:00 AM", "할리스커피", "#카드", "-3500원"))


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
    }
}