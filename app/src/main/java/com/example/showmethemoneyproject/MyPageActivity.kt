package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
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