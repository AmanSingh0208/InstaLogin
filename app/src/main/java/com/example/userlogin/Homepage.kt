package com.example.userlogin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        userWelcome()
    }

    @SuppressLint("SetTextI18n")
    private fun userWelcome() {
        val viewText = findViewById<TextView>(R.id.welcomeTextView)
        val viewImg = findViewById<ImageView>(R.id.imageViewUser)
        val bundle: Bundle? = intent.extras

        bundle.let {
            val msg = bundle!!.getString("name")
            viewText.text = "$msg"
        }

        bundle.let {
            viewImg.setImageResource(R.drawable.aman)}

    }
}