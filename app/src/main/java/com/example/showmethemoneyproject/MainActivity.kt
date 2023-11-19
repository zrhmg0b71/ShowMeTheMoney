package com.example.showmethemoneyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.showmethemoneyproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 번째 사진 클릭 시 두 번째 사진으로 전환됨
        binding.startImage1.setOnClickListener {
            binding.startImage1.visibility = View.INVISIBLE
            binding.startImage2.visibility = View.VISIBLE
        }
        // 두 번째 사진 클릭 시 세 번째 사진으로 전환됨
        binding.startImage2.setOnClickListener {
            binding.startImage2.visibility = View.INVISIBLE
            binding.startImage3.visibility = View.VISIBLE
        }
        // 세 번째 사진 클릭 시 첫 번째 사진으로 전환됨
        binding.startImage3.setOnClickListener {
            binding.startImage3.visibility = View.INVISIBLE
            binding.startImage1.visibility = View.VISIBLE
        }
    }
}