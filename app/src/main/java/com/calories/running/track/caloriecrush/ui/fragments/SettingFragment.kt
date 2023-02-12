package com.calories.running.track.caloriecrush.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentSettingBinding


class SettingFragment : Fragment(R.layout.fragment_setting) {


    private lateinit var binding: FragmentSettingBinding
    private var sharerdPreferences: SharedPreferences? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        sharerdPreferences = context?.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        setupSettingScreen()

        binding.linLayout.animation=AnimationUtils.loadAnimation(context,R.anim.slide_up)

        binding.cvName.setOnClickListener {

            if (binding.ivCheck.isVisible == false) {
                binding.etName.focusable = View.FOCUSABLE
                binding.etName.isFocusableInTouchMode = true
                binding.etName.requestFocus()
                val manager: InputMethodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(binding.etName, 0)
                binding.ivPencil.visibility = View.GONE
                binding.ivCheck.visibility = View.VISIBLE
            } else {
                applyChangesToPrefs()
                binding.etName.clearFocus()
                binding.ivPencil.visibility = View.VISIBLE
                binding.ivCheck.visibility = View.GONE
                Toast.makeText(context, "Changes Saved", Toast.LENGTH_SHORT).show()
            }

        }

        binding.cvWeight.setOnClickListener {
            if (binding.ivCheck2.isVisible == false) {
                binding.etWeight.focusable = View.FOCUSABLE
                binding.etWeight.isFocusableInTouchMode = true
                binding.etWeight.requestFocus()
                val manager: InputMethodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(binding.etWeight, 0)
                binding.ivPencil2.visibility = View.GONE
                binding.ivCheck2.visibility = View.VISIBLE
            } else {
                applyChangesToPrefs()
                binding.etWeight.clearFocus()
                binding.ivPencil2.visibility = View.VISIBLE
                binding.ivCheck2.visibility = View.GONE
                Toast.makeText(context, "Changes Saved", Toast.LENGTH_SHORT).show()
            }
        }


        binding.cvRateUs.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id= com.calories.running.track.caloriecrush")))
        }

        binding.cvContactUs.setOnClickListener {
            val intent=Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","yashquery30401@gmail.com",null))
            startActivity(Intent.createChooser(intent,"Send Mail..."))
        }

        binding.cvPrivacyPolicy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://docs.google.com/document/d/1yvdeVZd3pochir0ZXiubdfoDbHsXlRoiyjDpQ6UU9lU/")
                )
            )
        }

    }

    private fun applyChangesToPrefs() {
        val weightUnit = sharerdPreferences?.getString("weight_unit", "-1")


        if (weightUnit == "Kg") {

            sharerdPreferences?.edit {
                this.putString("user_name", binding.etName.text.toString()).apply()
                this.putFloat("weight", binding.etWeight.text.toString().toFloat()).apply()
            }

        } else {
            val weightInPounds = binding.etWeight.text.toString().toFloat() / 2.20462f
            sharerdPreferences?.edit {
                this.putString("user_name", binding.etName.text.toString()).apply()
                this.putFloat("weight", weightInPounds).apply()
            }
        }


    }

    private fun setupSettingScreen() {


        val weightUnit = sharerdPreferences?.getString("weight_unit", "-1")
        var weight = sharerdPreferences?.getFloat("weight", 0f)?.toFloat()
        Log.d("Weight", weightUnit.toString())

        binding.etName.setText(sharerdPreferences?.getString("user_name", "User's Name").toString())
        if (weightUnit == "Kg") {
            binding.weightUnit.text = "Kg"
            binding.etWeight.setText(weight.toString())
        } else {
            binding.weightUnit.text = "Pounds"
            weight = weight?.times(2.20462f)
            binding.etWeight.setText(weight.toString())
        }


    }
}
