package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.showmethemoneyproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewpager
        viewPager.adapter = MyFragmentPagerAdapter(this)

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 페이지가 선택되었을 때의 동작
                when (position) {
                    0 -> {
                        binding.startImageSelect1.setBackgroundResource(R.drawable.circle_button_blue)
                        binding.startImageSelect2.setBackgroundResource(R.drawable.circle_button_gray)
                        binding.startImageSelect3.setBackgroundResource(R.drawable.circle_button_gray)
                    }
                    1 -> {
                        binding.startImageSelect1.setBackgroundResource(R.drawable.circle_button_gray)
                        binding.startImageSelect2.setBackgroundResource(R.drawable.circle_button_blue)
                        binding.startImageSelect3.setBackgroundResource(R.drawable.circle_button_gray)
                    }
                    2 -> {
                        binding.startImageSelect1.setBackgroundResource(R.drawable.circle_button_gray)
                        binding.startImageSelect2.setBackgroundResource(R.drawable.circle_button_gray)
                        binding.startImageSelect3.setBackgroundResource(R.drawable.circle_button_blue)
                    }
                }
            }
        })

        binding.startImageSelect1.setOnClickListener {
            binding.startImageSelect1.setBackgroundResource(R.drawable.circle_button_blue)
            binding.startImageSelect2.setBackgroundResource(R.drawable.circle_button_gray)
            binding.startImageSelect3.setBackgroundResource(R.drawable.circle_button_gray)
            binding.viewpager.setCurrentItem(0, true)
        }
        binding.startImageSelect2.setOnClickListener {
            binding.startImageSelect1.setBackgroundResource(R.drawable.circle_button_gray)
            binding.startImageSelect2.setBackgroundResource(R.drawable.circle_button_blue)
            binding.startImageSelect3.setBackgroundResource(R.drawable.circle_button_gray)
            binding.viewpager.setCurrentItem(1, true)
        }
        binding.startImageSelect3.setOnClickListener {
            binding.startImageSelect1.setBackgroundResource(R.drawable.circle_button_gray)
            binding.startImageSelect2.setBackgroundResource(R.drawable.circle_button_gray)
            binding.startImageSelect3.setBackgroundResource(R.drawable.circle_button_blue)
            binding.viewpager.setCurrentItem(2, true)
        }

        // 각 사진 전환 시 밑에 동그라미 색 변환되는 것도 해야 함 ...ㅎ

        val intent1 = Intent(this, JoinActivity::class.java)
        binding.starting1.setOnClickListener{startActivity(intent1)}

//        val intent1 = Intent(this, MyPageActivity::class.java)
//        binding.starting1.setOnClickListener{startActivity(intent1)}

        val intent2 = Intent(this, LoginActivity::class.java)
        binding.goToLogin.setOnClickListener{startActivity(intent2)}
    }
}