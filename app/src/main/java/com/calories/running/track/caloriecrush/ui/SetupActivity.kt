package com.calories.running.track.caloriecrush.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.ActivitySetupBinding

class SetupActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySetupBinding
    private lateinit var slideUp: Animation
    private lateinit var fadeIn:Animation
    private lateinit var slideSlow:Animation
    private lateinit var slideLeft:Animation
    private lateinit var slideUpAndFadeIn:Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        slideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up)
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        slideSlow=AnimationUtils.loadAnimation(this,R.anim.slide_up_slow)
        slideLeft=AnimationUtils.loadAnimation(this,R.anim.slide_left)
        slideUpAndFadeIn=AnimationUtils.loadAnimation(this,R.anim.slide_up_and_fadein)

        startAnimation()

        binding.enterWeightBtn.setOnClickListener {
            binding.kgText.visibility= View.VISIBLE
            binding.numberPicker.visibility=View.VISIBLE

            binding.kgText.animation=slideUpAndFadeIn
            binding.numberPicker.animation=slideUpAndFadeIn
        }

    }

    private fun startAnimation() {
        binding.etName.animation=slideUp
        binding.imageView.animation=fadeIn
        binding.imageView.animation=fadeIn
        binding.enterWeightBtn.animation=slideSlow
        binding.continueBtn.animation=slideLeft
    }
}