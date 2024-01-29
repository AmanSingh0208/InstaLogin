package com.example.userlogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var database: DatabaseReference

class UserRegistration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_registration)
        val singin = findViewById<TextView>(R.id.singintext)
        val singup = findViewById<Button>(R.id.userSingup)
        val fullName = findViewById<EditText>(R.id.userName)
        val userEmail = findViewById<EditText>(R.id.userMail)
        val password = findViewById<EditText>(R.id.password)
        val confirmPass = findViewById<EditText>(R.id.confirmPassword)
        val mobileNo = findViewById<EditText>(R.id.mobile)

        singup.setOnClickListener {
            val name = fullName.text.toString()
            val email = userEmail.text.toString()
            val inputPassword = password.text.toString()
            val inputConfirmPass = confirmPass.text.toString()
            val mobile = mobileNo.text.toString()

            val user = User(name, email, inputPassword, mobile)

            if (inputPassword == inputConfirmPass) {

                database = FirebaseDatabase.getInstance().getReference("Users")
                database.child(mobile).setValue(user).addOnSuccessListener {
                    showToast("User registered successfully.. Please Login")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                showToast("Password mismatch!!")
            }


        }

        singin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
