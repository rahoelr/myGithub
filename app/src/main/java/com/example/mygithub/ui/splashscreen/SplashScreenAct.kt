package com.example.mygithub.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mygithub.R
import com.example.mygithub.ui.MainActivity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate


class SplashScreenAct : AppCompatActivity() {

    private val SPLASH_DURATION = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref: SharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val isDarkModeActive: Boolean = pref.getBoolean("isDarkModeActive", false)


        if (isDarkModeActive) {
            AppCompatDelegate.MODE_NIGHT_YES
            setContentView(R.layout.splash_screen_white)
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
            setContentView(R.layout.activity_splash_screen)
        }

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DURATION.toLong())
    }
}
