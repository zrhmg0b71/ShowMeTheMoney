package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val intent1 = Intent(this, JoinActivity::class.java)
        val intent1 = Intent(this, SetUpGoalActivity::class.java)
        val intent2 = Intent(this, MonthSpendActivity::class.java)
        binding.goToJoin.setOnClickListener{startActivity(intent1)}

    }
}