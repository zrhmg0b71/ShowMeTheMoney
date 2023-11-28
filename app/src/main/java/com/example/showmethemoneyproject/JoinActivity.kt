package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent1 = Intent(this, LoginActivity::class.java)
        //binding.joinBtn.setOnClickListener{startActivity(intent1)}
        binding.goToLogin.setOnClickListener{startActivity(intent1)}

        // FirebaseAuth 인스턴스 초기화
        auth = Firebase.auth

        binding.joinBtn.setOnClickListener {
            var isGoToJoin = true
            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()
            val passwordCheck = binding.passwordCheckArea.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (passwordCheck.isEmpty()){
                Toast.makeText(this, "비밀번호 확인이 필요합니다", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password != passwordCheck) {
                Toast.makeText(this, "비밀번호 입력이 다릅니다. 다시 확인해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            if (password != passwordCheck) {
                Toast.makeText(this, "비밀번호 입력이 다릅니다. 다시 확인해주세요", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if(isGoToJoin) {

                Log.d("JOIN", email)
                Log.d("JOIN", password)

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(this, "회원가입에 성공했습니다. 로그인해 주세요.", Toast.LENGTH_LONG).show()
                            startActivity(intent1)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

}