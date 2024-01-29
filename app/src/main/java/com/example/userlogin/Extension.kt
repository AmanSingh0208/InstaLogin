package com.example.userlogin

import android.content.Context
import android.widget.Toast
import java.time.Duration

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}