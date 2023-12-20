package com.example.showmethemoneyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityMonthlyBinding
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding

class MonthlyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMonthlyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}