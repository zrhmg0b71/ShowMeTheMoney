package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.showmethemoneyproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent1 = Intent(this, JoinActivity::class.java)
        binding.goToJoin.setOnClickListener{startActivity(intent1)}
    }
}