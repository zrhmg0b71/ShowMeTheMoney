package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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

        // 각 사진 전환 시 밑에 동그라미 색 변환되는 것도 해야 함 ...ㅎ

//        val intent1 = Intent(this, JoinActivity::class.java)
//        binding.starting1.setOnClickListener{startActivity(intent1)}

        val intent1 = Intent(this, MyPageActivity::class.java)
        binding.starting1.setOnClickListener{startActivity(intent1)}

        val intent2 = Intent(this, LoginActivity::class.java)
        binding.goToLogin.setOnClickListener{startActivity(intent2)}
    }
}