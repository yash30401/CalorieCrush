package com.calories.running.track.caloriecrush.ui

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
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

        startAnimation()// Starting Button And Editext Animation when First Time Screen Starts

        binding.enterWeightBtn.setOnClickListener {

            binding.numberPicker.visibility=View.VISIBLE
            binding.chipGroup.visibility=View.VISIBLE
            binding.chipGroup.animation=slideUpAndFadeIn
            binding.numberPicker.animation=slideUpAndFadeIn
        }

        binding.kgChip.setOnClickListener {
            binding.poundsChip.isCheckable=false
            binding.poundsChip.chipStrokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.hintColor))
            binding.poundsChip.chipBackgroundColor =
                ColorStateList.valueOf(resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
            binding.poundsChip.setTextColor(resources.getColor(R.color.black))



            binding.kgChip.isCheckable=true

                binding.kgChip.chipStrokeColor =
                    ColorStateList.valueOf(resources.getColor(R.color.bottomBarDark))
                binding.kgChip.chipBackgroundColor =
                    ColorStateList.valueOf(resources.getColor(R.color.chipBg))
                binding.kgChip.setTextColor(resources.getColor(R.color.bottomBarDark))


        }

        binding.poundsChip.setOnClickListener {

            binding.kgChip.isCheckable=false
            binding.kgChip.chipStrokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.hintColor))
            binding.kgChip.chipBackgroundColor =
                ColorStateList.valueOf(resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
            binding.kgChip.setTextColor(resources.getColor(R.color.black))

            binding.poundsChip.isCheckable=true

            binding.poundsChip.chipStrokeColor =
                ColorStateList.valueOf(resources.getColor(R.color.bottomBarDark))
            binding.poundsChip.chipBackgroundColor =
                ColorStateList.valueOf(resources.getColor(R.color.chipBg))
            binding.poundsChip.setTextColor(resources.getColor(R.color.bottomBarDark))


        }

        binding.continueBtn.setOnClickListener {
            checkValidation()
        }

    }

    private fun checkValidation() {
        val name=binding.etName.editText?.text.toString()
        if(name.isEmpty()){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
        }else if(binding.chipGroup.visibility==View.GONE){
            Toast.makeText(this, "Please Enter Your Weight", Toast.LENGTH_SHORT).show()
        }else if(binding.kgChip.isChecked==false && binding.poundsChip.isChecked==false){
            Toast.makeText(this, "Please Select Your Weight Units", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startAnimation() {
        binding.etName.animation=slideUp
//        binding.imageView.animation=fadeIn

        binding.enterWeightBtn.animation=slideSlow
        binding.continueBtn.animation=slideLeft
    }
}