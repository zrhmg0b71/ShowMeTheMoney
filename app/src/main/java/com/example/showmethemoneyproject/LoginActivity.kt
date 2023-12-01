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

        val intent1 = Intent(this, JoinActivity::class.java)
        binding.goToJoin.setOnClickListener{startActivity(intent1)}

        auth = Firebase.auth

        binding.starting2.setOnClickListener{
            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()

                        val intent2 = Intent(this, FirstpageActivity::class.java)
                        startActivity(intent2)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}