package com.example.showmethemoneyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityMyPageBinding
import com.example.showmethemoneyproject.databinding.ActivitySavingPlanBinding

class SavingPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavingPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}