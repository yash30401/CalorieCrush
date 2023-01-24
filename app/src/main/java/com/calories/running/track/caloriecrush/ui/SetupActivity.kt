package com.calories.running.track.caloriecrush.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.ActivitySetupBinding

class SetupActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}