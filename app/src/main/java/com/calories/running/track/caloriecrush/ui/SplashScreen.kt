package com.calories.running.track.caloriecrush.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.other.Constants.SPLASH_DISPLAY_VAL

class SplashScreen : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            // Start main activity
            startActivity(Intent(this, SetupActivity::class.java))
            finish()
        }, SPLASH_DISPLAY_VAL.toLong())

    }
}