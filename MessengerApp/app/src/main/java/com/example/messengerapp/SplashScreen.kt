package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_Translucent)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val intent= Intent(this,SignInActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        },2000)
    }
}