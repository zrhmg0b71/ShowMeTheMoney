package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityMonthlyBinding
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding

class MonthlyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMonthlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentFirstPage = Intent(this, FirstpageActivity::class.java)
        val intentSetUpGoalPage = Intent(this, SetUpGoalActivity::class.java)
        val intentMonthSpendPage = Intent(this, MonthSpendActivity::class.java)
        val intentMyPage = Intent(this, MyPageActivity::class.java)

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.footer_home -> startActivity(intentFirstPage)
                R.id.footer_wallet -> startActivity(intentSetUpGoalPage)
                R.id.footer_calendar-> startActivity(intentMonthSpendPage)
                R.id.footer_mypage -> startActivity(intentMyPage)
            }
            true
        }
    }
}