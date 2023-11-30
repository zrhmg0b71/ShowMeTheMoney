package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityLoginBinding
import com.example.showmethemoneyproject.databinding.ActivityMonthSpendBinding

class MonthSpendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMonthSpendBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}