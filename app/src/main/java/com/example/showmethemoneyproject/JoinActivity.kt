package com.example.showmethemoneyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.showmethemoneyproject.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
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
            if (password.length < 6){
                Toast.makeText(this, "비밀번호를 6자리 이상으로 입력해주세요", Toast.LENGTH_LONG).show()
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

                            //유저 정보 불러오기
                            val userName = binding.userName.text.toString()
                            val phoneNumber = binding.userPhoneNumber.text.toString()
                            val birth = binding.userBirth.text.toString()

                            //DB에 입력된 유저 정보 저장
                            val database = Firebase.database
                            val userInfoRef = database.getReference("userInfo")

                            //불러온 유저 정보를 데이터 클래스에 저장
                            val userInfo = UserInfoModel(userName, email, phoneNumber, birth)

                            //DB가 JSON 트리 형식으로 되어 있음
                            //유저 정보를 담은 데이터 클래스를 현재 유저의 uid 아래에 setValue로 넣어주면 JSON형식으로 들어감
                            userInfoRef
                                .child(auth.currentUser!!.uid)  //setValue()는 추가가 아니라 갱신 방식이라 이거 빼먹으면 다른 유저 정보 날아갈 수 있으니 이거 꼭!!
                                .setValue(userInfo)

                            //화면전환
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