package com.example.userlogin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val btnHome = findViewById<ImageButton>(R.id.imageButtonHome)
        val btnSearch = findViewById<ImageButton>(R.id.imageButtonSearch)
        val btnProfile = findViewById<ImageButton>(R.id.imageButtonProfile)

        replaceFrameWithFragment(Home_Fragment())

//        userWelcome()

        btnHome.setOnClickListener {
            replaceFrameWithFragment(Home_Fragment())
        }

        btnSearch.setOnClickListener {
            replaceFrameWithFragment(Clock_Fragment())
        }

        btnProfile.setOnClickListener {
            replaceFrameWithFragment(Profile_Fragment())
        }
    }

    private fun replaceFrameWithFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun userWelcome() {
        val viewText = findViewById<TextView>(R.id.welcomeTextView)
        val viewImg = findViewById<ImageView>(R.id.imageViewUser)
        val bundle: Bundle? = intent.extras

        bundle.let {
            val msg = bundle!!.getString(MainActivity.KEY)
            viewText.text = "$msg"
        }

        bundle.let {
            viewImg.setImageResource(R.drawable.aman)
        }

    }
}