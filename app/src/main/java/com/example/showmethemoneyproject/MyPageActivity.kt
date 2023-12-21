package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class MyPageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // 월은 0부터 시작하므로 +1 해줍니다.
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        binding.userInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, UserInfo::class.java)
            startActivity(intent)
        }

        binding.setNotification.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, SetNotification::class.java)
            startActivity(intent)
        }

        binding.myAccount.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, MyAccount::class.java)
            startActivity(intent)
        }

        binding.calendar.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,Calendar::class.java)
            startActivity(intent)
        }

        binding.weekResult.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, WeekResult::class.java)
            startActivity(intent)
        }

        binding.monthResult.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, MonthResult::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Logout::class.java)
            startActivity(intent)
        }*/

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        val currentTimetable = (year.toString() + (month + 1).toString())
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userInfoRef: DatabaseReference =
            database.getReference("userInfo/${auth.currentUser!!.uid}")

        userInfoRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val name = dataSnapshot.child("name").value.toString()
                    binding.username.text = name
                } else {
                    binding.username.text = "이름 없는 사용자"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리 로직을 여기에 작성
                println("Error: ${databaseError.message}")
            }
        })


        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)

        binding.navigationView.selectedItemId = R.id.footer_mypage
        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
            }
            true
        }


    }
}