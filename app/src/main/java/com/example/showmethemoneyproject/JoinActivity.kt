package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent1 = Intent(this, LoginActivity::class.java)
        binding.goToJoin.setOnClickListener{startActivity(intent1)}
        binding.goToLogin.setOnClickListener{startActivity(intent1)}

    }
}